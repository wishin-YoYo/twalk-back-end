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
import server.twalk.Walking.dto.JalkingDto;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.entity.Jalking;
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.StatusNotFoundException;
import server.twalk.Walking.repository.JalkingRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JalkingService {

    private final JalkingRepository jalkingRepository;
    private final MemberRepository memberRepository;
    private final StatusRepository statusRepository;

    // jalking 생성(요청)
    @Transactional
    public IdResponse create(JalkingReq req ) {

        Member requestMember = memberRepository.findById(req.getRequesterId()).orElseThrow(MemberNotFoundException::new);
        Member receivedMember = memberRepository.findById(req.getReceiverId()).orElseThrow(MemberNotFoundException::new);

        Jalking jalking = jalkingRepository.save(
                Jalking.builder()
                        .status(statusRepository.findByStatusType(StatusType.ONGOING).orElseThrow(StatusNotFoundException::new))
                        .requester(requestMember)
                        .receiver(receivedMember)
                        .build()
        );

        return new IdResponse(jalking.getId());
    }


    // jalking 승인 (receiver)
    @Transactional
    public IdResponse approve(Long jalkingId) {

        Jalking jalking = jalkingRepository.findById(jalkingId).orElseThrow(JalkingNotFoundException::new);
        jalking.setStatus(statusRepository.findByStatusType(StatusType.APPROVED).orElseThrow(StatusNotFoundException::new));
        return new IdResponse(jalking.getId());

    }

    // jalking 거절 (receiver)
    @Transactional
    public IdResponse rejected(Long jalkingId) {

        Jalking jalking = jalkingRepository.findById(jalkingId).orElseThrow(JalkingNotFoundException::new);
        jalking.setStatus(statusRepository.findByStatusType(StatusType.REJECTED).orElseThrow(StatusNotFoundException::new));
        return new IdResponse(jalking.getId());

    }
    
    // jalking 취소 => 삭제 (requester 이)
    @Transactional
    public IdResponse cancel(Long jalkingId) {

        Jalking jalking = jalkingRepository.findById(jalkingId).orElseThrow(JalkingNotFoundException::new);
        jalkingRepository.delete(jalking);
        return new IdResponse(jalking.getId());

    }

    // jalking 조회
    @Transactional
    public JalkingDto read(Long jalkingId) {

        Jalking jalking = jalkingRepository.findById(jalkingId).orElseThrow(JalkingNotFoundException::new);
        return JalkingDto.toDto(jalking);

    }

    // jalking 종료
    @Transactional
    public Long end(Long jalkingId) {

        Jalking jalking = jalkingRepository.findById(jalkingId).orElseThrow(JalkingNotFoundException::new);
        jalking.setStatus(statusRepository.findByStatusType(StatusType.COMPLETE).orElseThrow(StatusNotFoundException::new));
        return jalking.getId();

    }

}
