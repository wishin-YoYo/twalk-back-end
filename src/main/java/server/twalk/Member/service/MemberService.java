package server.twalk.Member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.twalk.Member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${default.image.address}")
    private String defaultImageAddress;
    private final MemberRepository memberRepository;

//    public MemberDto read(Long id){
//
//    }
//
}
