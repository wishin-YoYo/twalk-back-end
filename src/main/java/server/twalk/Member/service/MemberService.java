package server.twalk.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.dto.MemberDto;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.Walking.repository.WalkingRepository;
import server.twalk.Walking.service.WalkingCommonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${default.image.address}")
    private String defaultImageAddress;
    private final MemberRepository memberRepository;
    private final WalkingRepository walkingRepository;

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
        double targetLon = targetMember.getLon();
        double targetLat = targetMember.getLat();

        List<Member> allMember = memberRepository.findAll();
        List<Member> membersAround = new ArrayList<>();

        for ( Member cmpMem : allMember ){

            // target user 와 cmp user (비교 대상, 검사 대상) 사이의 거리가 1000미터 이하면 주변인으로 추가
            if(WalkingCommonService.distance(
                    targetLon,
                    targetLat,
                    cmpMem.getLon(),
                    cmpMem.getLat(),
                    "meter"
            )<1000){
                membersAround.add(cmpMem);
            }
        }

        return membersAround.stream().map(
                MemberDto::from
        ).collect(Collectors.toList());
    }

}
