package server.twalk.Walking.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.Walking.dto.WalkingDto;
import server.twalk.Walking.dto.request.WalkingReq;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.entity.Walking;
import server.twalk.Walking.exception.StatusNotFoundException;
import server.twalk.Walking.exception.WalkingNotFoundException;
import server.twalk.Walking.repository.WalkingRepository;

@RequiredArgsConstructor
@Service
public class WalkingService {

    private final WalkingRepository walkingRepository;
    private final MemberRepository memberRepository;
    private final StatusRepository statusRepository;

    // 최초 walking
    @Transactional
    public IdResponse create(WalkingReq req ) {

        Member member = memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new);

        Walking walking = walkingRepository.save(
                Walking.builder()
                        .status(statusRepository.findByStatusType(StatusType.ONGOING).orElseThrow(StatusNotFoundException::new))
                        .walkingCount(0)
                        .member(member)
                        .build()
        );

        // 최초의 lat, lon
        walking.addLatLon(new LatLonPair(req.getLat(), req.getLon(), walking));

        return new IdResponse(walking.getId());
    }

    @Transactional
    public IdResponse update(WalkingReq req, Long walkingId ) {

        Walking walking = walkingRepository.findById(walkingId).orElseThrow(WalkingNotFoundException::new);

        // 추가 이동한 lat, lon
         walking.addLatLon(new LatLonPair(req.getLat(), req.getLon(), walking));

        return new IdResponse(walking.getId());
    }

    @Transactional
    public WalkingDto read( Long walkingId ) {

        Walking walking = walkingRepository.findById(walkingId).orElseThrow(WalkingNotFoundException::new);

        return WalkingDto.toDto(walking);
    }

    @Transactional
    public IdResponse end(WalkingReq req, Long walkingId ) {

        Member member = memberRepository.findById(req.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Walking walking = walkingRepository.findById(walkingId).orElseThrow(WalkingNotFoundException::new);

        // 추가 이동한 lat, lon
        //walking.addLatLon(req.getLat(), req.getLon());
        // walking 에 distance 갱신
        walking.addDistance(req.getDistance());
        // 완료료 변경
        walking.updateStatus(statusRepository.findByStatusType(StatusType.COMPLETE).orElseThrow(StatusNotFoundException::new));
        // 멤버에게 걸음수 갱신
        member.addTotalDistanceAndCalories(walking.getDistance());

        return new IdResponse(walking.getId());
    }

}