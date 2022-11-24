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
import server.twalk.PvP.entity.PvpModeType;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.repository.PvpMatchRepository;
import server.twalk.PvP.repository.PvpModeRepository;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.StatusNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PvPService {

    private final PvpMatchRepository pvpMatchRepository;
    private final PvpModeRepository pvpModeRepository;
    private final StatusRepository statusRepository;
    private final MemberRepository memberRepository;

    // pvp 매칭 시작
    @Transactional
    public IdResponse create(PvpReq req ) {

        Member requestMember = memberRepository.findById(req.getRequesterId()).orElseThrow(MemberNotFoundException::new);
        Member receivedMember = memberRepository.findById(req.getReceiverId()).orElseThrow(MemberNotFoundException::new);

        PvpMatch pvpMatch = pvpMatchRepository.save(
                PvpMatch.builder()
                        .pvpMode(pvpModeRepository.findByPvpModeType(PvpModeType.valueOf(req.getPvpMode())))
                        .status(statusRepository.findByStatusType(StatusType.ONGOING).orElseThrow(StatusNotFoundException::new))
                        .requester(requestMember)
                        .receiver(receivedMember)
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

    // pvp 조회
    @Transactional
    public PvpMatchDto read(Long pvpId) {
        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(JalkingNotFoundException::new);
        return PvpMatchDto.toDto(pvpMatch);

    }

    // 내가 만든 pvp 조회
    @Transactional
    public List<PvpMatchDto> readRequestPvp(PvpReq req) {

        Member requester = memberRepository.findById(req.getRequesterId()).orElseThrow(MemberNotFoundException::new);
        return PvpMatchDto.toDtoList(pvpMatchRepository.findByRequester(requester));

    }

    @Transactional
    public List<PvpMatchDto> readReceivedPvps(PvpReq req) {

        Member receiver = memberRepository.findById(req.getReceiverId()).orElseThrow(MemberNotFoundException::new);
        return PvpMatchDto.toDtoList(
                pvpMatchRepository.findByReceiver(receiver).stream()
                        .filter(
                                jalking -> !jalking.getStatus().getStatusType().equals(StatusType.ONGOING)
                        )

                        .collect(Collectors.toList())
        );

    }

}
