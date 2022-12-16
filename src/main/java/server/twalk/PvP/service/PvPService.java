package server.twalk.PvP.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.dto.PvpMatchDto;
import server.twalk.PvP.dto.PvpMoveReq;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.PvP.entity.PvpMode;
import server.twalk.PvP.entity.PvpModeType;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.exception.PvpNotFoundException;
import server.twalk.PvP.repository.PvpMatchRepository;
import server.twalk.PvP.repository.PvpModeRepository;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.Walking.dto.LatLonPairDto;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.LatLonPairNotFoundException;
import server.twalk.Walking.exception.StatusNotFoundException;
import server.twalk.Walking.repository.LatLonPairRepository;
import server.twalk.Walking.service.WalkingCommonService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

        Long targetLocationId = null;
        if(receivedMember.getId()!=1L){
            targetLocationId = 40000000000l + receivedMember.getId();
        }else{
            targetLocationId = 40000000000l + requestMember.getId();
        }

        targetLocation = latLonPairRepository.findById(targetLocationId).orElseThrow(LatLonPairNotFoundException::new);

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

        PvpMatch pvpMatch = pvpMatchRepository.findById(pvpId).orElseThrow(PvpNotFoundException::new);
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

    public static List<LatLonPair> interiorDivision(double lat1, double lon1, double lat2, double lon2, int time){
        // m:n 내분
        List<LatLonPair> returnLatLonPair = new ArrayList<>();
        double m = 1;
        double n = time-1;

        while(m<=n) {
            double q = (n * lat1 + m * lat2) / (m + n);
            double r = (n * lon1 + m * lon2) / (m + n);
            System.out.println(q + " " + r + " \n");
            returnLatLonPair.add(new LatLonPair(q,r));
            m++;
        }
        return returnLatLonPair;
    }

    @Transactional
    public List<LatLonPairDto> move(Long pvpId, PvpMoveReq req) throws InterruptedException {
        PvpMatch pvp = pvpMatchRepository.findById(pvpId).orElseThrow(PvpNotFoundException::new);
        Member mover = null;
        LatLonPair targetLocation = pvp.getTargetLocation();
        int time = 0; // 일정 시간동안 돌 것

        if(pvp.getReceiver().getId().equals(1L)){
            mover = pvp.getRequester();
        }else{
            mover = pvp.getReceiver();
        }

        // req.getTime() 초 만큼 for 문 돌면서 thread sleep 하면서 유저 current 위치 변경

        List<LatLonPair> moveList = interiorDivision(
                targetLocation.getLat(), targetLocation.getLon(),
                mover.getLatLonPair().getLat(), mover.getLatLonPair().getLon(),
                req.getTime()
        );
        List<Long> ids = new ArrayList<>();
        while ( time < moveList.size() ){
            LatLonPair latLonPair = latLonPairRepository.save(moveList.get(time));
            ids.add(latLonPair.getId());
            time++;
        }
        time = 0;
//        while ( time < ids.size() ){
//            Thread.sleep(1000); // 1초마다 이동한다.
//            LatLonPair latLonPair = latLonPairRepository.findById(ids.get(time))
//                    .orElseThrow(LatLonPairNotFoundException::new);
//            mover.updateMyLocation(latLonPair);
//            memberRepository.latLonPairUpdate(latLonPair, mover.getId() );
//            System.out.println("현재 시간 : " + LocalTime.now() + "현재 user 위치 : " + mover.getLatLonPair().getId() + "user 이동 몇번째 ? "+time +"\n");
//            time++;
//        }
//        update(mover, ids);
        Thread.sleep(20000); // 15초 안에 내가 10번 하기
        moveList.add(new LatLonPair(targetLocation.getLat(), targetLocation.getLon()));
        mover.updateMyLocation(new LatLonPair(targetLocation.getLat(), targetLocation.getLon()));
        end(pvpId, mover.getId()); // pvp 종료되고 mover 가 승리자가 된다.
        //System.out.println(mover.getWins() + " 이긴 애" + mover.getId());
        return LatLonPairDto.toDtoList(moveList);
    }
//
//    public void update(Member mover, List<Long> ids) throws InterruptedException {
//        int time=0;
//            while ( time < ids.size() ) {
//                Thread.sleep(1000); // 1초마다 이동한다.
//                LatLonPair latLonPair = latLonPairRepository.findById(ids.get(time))
//                        .orElseThrow(LatLonPairNotFoundException::new);
//                mover.updateMyLocation(latLonPair);
//                memberRepository.latLonPairUpdate(latLonPair, mover.getId());
//                System.out.println("현재 시간 : " + LocalTime.now() + "현재 user 위치 : " + mover.getLatLonPair().getId() + "user 이동 몇번째 ? " + time + "\n");
//                time++;
//            }
//    }

    @Transactional
    public Long updateSeperate(Long pvpId, Long lid) throws InterruptedException {
        LatLonPair latLonPair = latLonPairRepository.findById(lid).orElseThrow(LatLonPairNotFoundException::new);
        PvpMatch pvp = pvpMatchRepository.findById(pvpId).orElseThrow(PvpNotFoundException::new);
        Member mover = pvp.getRequester();
//        mover.updateMyLocation(latLonPair);
        memberRepository.latLonPairUpdate(latLonPair, mover.getId());
        return pvpId;
    }

}
