package server.twalk.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.dto.MemberDto;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.dto.PvpMatchDto;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.repository.PvpMatchRepository;
import server.twalk.Walking.dto.JalkingDto;
import server.twalk.Walking.dto.LatLonPairDto;
import server.twalk.Walking.dto.WalkingDto;
import server.twalk.Walking.dto.request.WalkingReq;
import server.twalk.Walking.entity.Jalking;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.entity.Walking;
import server.twalk.Walking.repository.JalkingRepository;
import server.twalk.Walking.repository.WalkingRepository;
import server.twalk.Walking.service.WalkingCommonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final WalkingRepository walkingRepository;
    private final JalkingRepository jalkingRepository;
    private final PvpMatchRepository pvpMatchRepository;

    // 멤버의 정보를 전달해주는 것
    public MemberDto read(Long id){
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return MemberDto.from(member);
    }

    // 멤버의 타인 노출 여부 설정
    public IdResponse show(Long id){
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.showMyLocation();
        return new IdResponse(id);
    }

    // 멤버의 걷기 상태 여부 설정
    public IdResponse activated(Long id){
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.activateMyStatus();
        return new IdResponse(id);
    }

    public List<MemberDto> readAll(){
        return memberRepository.findAll().stream().map(
                i -> MemberDto.from(i)
        ).collect(Collectors.toList());
    }

    public List<MemberDto> readAround(Long targetId){

        Member targetMember = memberRepository.findById(targetId).orElseThrow(MemberNotFoundException::new);
        double targetLon = targetMember.getLatLonPair().getLon();
        double targetLat = targetMember.getLatLonPair().getLat();

        List<Member> allMember = memberRepository.findAll();
        List<Member> membersAround = new ArrayList<>();

        for ( Member cmpMem : allMember ){

            // target user 와 cmp user (비교 대상, 검사 대상) 사이의 거리가 1000미터 이하면 주변인으로 추가
            if(WalkingCommonService.distance(
                    targetLon,
                    targetLat,
                    cmpMem.getLatLonPair().getLon(),
                    cmpMem.getLatLonPair().getLat(),
                    "meter"
            )<1000){
                if(cmpMem.getId()!=targetId) membersAround.add(cmpMem);
            }
        }

        return membersAround.stream().map(
                MemberDto::from
        ).collect(Collectors.toList());
    }

    // 현재 내 위치 주는 것
    public LatLonPairDto myLocation(Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return LatLonPairDto.toDto(member.getLatLonPair());
    }

    // 나의 걷기 기록 모두
    @Transactional
    public List<WalkingDto> readMineAll(Long memberId ) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<Walking> walks = walkingRepository.findByMemberOrderByIdDesc(member);

        return WalkingDto.toDtoList(walks);
    }

    @Transactional
    public IdResponse updateMyLocation(Long memberId, WalkingReq req) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.updateMyLocation(new LatLonPair(req.getLat(), req.getLon()));

        return new IdResponse(memberId);

    }

    // 내가 요청한 jalking list ( 내가 request 로 지정된 것)
    @Transactional
    public JalkingDto readRequestJalkings(Long id) {

        Member requester = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        List<Jalking> jalkings = jalkingRepository.findByRequesterByOrderByCreatedAtDesc(requester).stream()
                        .filter( jalking -> jalking.getStatus().getStatusType().name().equals("ONGOING") )
                        .collect(Collectors.toList());

        return jalkings.size()>0? JalkingDto.toDto(jalkings.get(0)) :new JalkingDto();

    }

    // 내게 요청 온 jalking list (내가 receiver 로 지정된 것)
    // 이때 현재 진행중인 것만 나에게 들어온 요청에 뜨게 하기
    @Transactional
    public JalkingDto readReceivedJalkings(Long id) {

        Member receiver = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
//        return JalkingDto.toDtoList(
//                jalkingRepository.findByReceiverByOrderByCreatedAtDesc(receiver).stream()
//                        .filter(
//                                jalking -> jalking.getStatus().getStatusType().name().equals("ONGOING")
//                        )
//                        .collect(Collectors.toList())
//        );
        List<Jalking> jalkings = jalkingRepository.findByReceiverByOrderByCreatedAtDesc(receiver).stream()
                .filter( jalking -> jalking.getStatus().getStatusType().name().equals("ONGOING") )
                .collect(Collectors.toList());

        return jalkings.size()>0? JalkingDto.toDto(jalkings.get(0)) :new JalkingDto();
    }


    // 내가 만든 pvp 조회
    @Transactional
    public List<PvpMatchDto> readRequestPvp(Long id) {

        Member requester = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return PvpMatchDto.toDtoList(pvpMatchRepository.findByRequester(requester));

    }

    @Transactional
    public List<PvpMatchDto> readReceivedPvps(Long id) {

        Member receiver = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return PvpMatchDto.toDtoList(
                pvpMatchRepository.findByReceiver(receiver).stream()
                        .filter(
                                pvpMatch -> pvpMatch.getStatus().getStatusType().name().equals("ONGOING")
                        )
                        .collect(Collectors.toList())
        );
    }


}
