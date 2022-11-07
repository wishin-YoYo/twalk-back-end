package server.twalk.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.dto.MemberDto;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${default.image.address}")
    private String defaultImageAddress;
    private final MemberRepository memberRepository;

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

}
