package server.twalk.PvP.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.dto.PvpMatchDto;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.PvP.entity.PvpMode;
import server.twalk.PvP.entity.PvpModeType;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.repository.PvpMatchRepository;
import server.twalk.PvP.repository.PvpModeRepository;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.LatLonPairNotFoundException;
import server.twalk.Walking.exception.StatusNotFoundException;
import server.twalk.Walking.repository.LatLonPairRepository;

import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PvPService {

    private final PvpMatchRepository pvpMatchRepository;
    private final PvpModeRepository pvpModeRepository;
    private final StatusRepository statusRepository;
    private final MemberRepository memberRepository;
    private final LatLonPairRepository latLonPairRepository;

    // pvp 매칭 시작
    @Transactional
    public IdResponse create(PvpReq req ) {

        Member requestMember = memberRepository.findById(req.getRequesterId()).orElseThrow(MemberNotFoundException::new);
        Member receivedMember = memberRepository.findById(req.getReceiverId()).orElseThrow(MemberNotFoundException::new);

        // targetLocation 이 true 인 pvpMode 는 하나 뿐 ( SOGANG, IFC 중 하나 )
        PvpMode pvpMode = pvpModeRepository.findAll().stream().filter(
                PvpMode::isTargetLocation
        ).collect(Collectors.toList()).get(0);

        LatLonPair targetLocation = null;

        if(pvpMode.getPvpModeType().name().equals("SOGANG")){
            targetLocation = latLonPairRepository.findById(30000000001L).orElseThrow(LatLonPairNotFoundException::new);
        }else if(pvpMode.getPvpModeType().name().equals("IFC")) {
            targetLocation = latLonPairRepository.findById(30000000002L).orElseThrow(LatLonPairNotFoundException::new);
        }

        PvpMatch pvpMatch = pvpMatchRepository.save(
                PvpMatch.builder()
                        .pvpMode(pvpModeRepository.findByPvpModeType(PvpModeType.valueOf("FLAG")))
                        .status(statusRepository.findByStatusType(StatusType.ONGOING).orElseThrow(StatusNotFoundException::new))
                        .requester(requestMember)
                        .receiver(receivedMember)
                        .targetLocation(targetLocation)
                        .build()
        );

        return new IdResponse(pvpMatch.getId());
    }

    // pvp 승인 - status 변경
    @Transactional
    public IdResponse approve(Long pvpId) {

        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        pvpMatch.setStatus(statusRepository.findByStatusType(StatusType.APPROVED).orElseThrow(StatusNotFoundException::new));
        return new IdResponse(pvpMatch.getId());

    }

    // pvp 거절
    @Transactional
    public IdResponse rejected(Long pvpId) {

        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        pvpMatch.setStatus(statusRepository.findByStatusType(StatusType.REJECTED).orElseThrow(StatusNotFoundException::new));
        return new IdResponse(pvpMatch.getId());

    }

    // pvp 취소
    @Transactional
    public IdResponse cancel(Long pvpId) {

        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        pvpMatchRepository.delete(pvpMatch);
        return new IdResponse(pvpMatch.getId());

    }

    // pvp 목표 위치 설정
    @Transactional
    public IdResponse setLocation(Long pvpId, PvpReq req) {

        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        pvpMatch.setTargetLocation(new LatLonPair(req.getLat(), req.getLon()));
        return new IdResponse(pvpMatch.getId());

    }

    // pvp 조회
    @Transactional
    public PvpMatchDto read(Long pvpId) {
        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        return PvpMatchDto.toDto(pvpMatch);

    }

    // pvp 목표 위치 설정
    @Transactional
    public IdResponse end(Long pvpId, Long winnerId) {

        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        pvpMatch.setStatus(statusRepository.findByStatusType(StatusType.COMPLETE).orElseThrow(StatusNotFoundException::new));

        Member requester = pvpMatch.getRequester();
        Member receiver = pvpMatch.getReceiver();

        if(Objects.equals(pvpMatch.getReceiver().getId(), winnerId)){
            pvpMatch.setWinner(receiver);
            receiver.updateWins();
            requester.updatLose();
        }else{
            pvpMatch.setWinner(requester);
            requester.updateWins();
            receiver.updatLose();
        };
        return new IdResponse(pvpMatch.getId());

    }

}
