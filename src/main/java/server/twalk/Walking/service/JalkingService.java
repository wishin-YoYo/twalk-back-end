package server.twalk.Walking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Common.entity.response.IdResponse;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.dto.PvpMoveReq;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.exception.PvpNotFoundException;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.Walking.dto.JalkingDto;
import server.twalk.Walking.dto.LatLonPairDto;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.entity.Jalking;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.LatLonPairNotFoundException;
import server.twalk.Walking.exception.StatusNotFoundException;
import server.twalk.Walking.repository.JalkingRepository;
import server.twalk.Walking.repository.LatLonPairRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JalkingService {

    private final JalkingRepository jalkingRepository;
    private final MemberRepository memberRepository;
    private final StatusRepository statusRepository;
    private final LatLonPairRepository latLonPairRepository;

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
//
//    @Transactional
//    public List<LatLonPairDto> move(Long jId ) throws InterruptedException {
//        Jalking jalking = jalkingRepository.findById(jId).orElseThrow(PvpNotFoundException::new);
//        Member mover = null;
//        Member notMover = null;
//
//        int time = 0; // 일정 시간동안 돌 것
//
//        if(jalking.getReceiver().getId().equals(1L)){
//            mover = jalking.getRequester();
//            notMover = jalking.getReceiver();
//        }else{
//            mover = jalking.getReceiver();
//            notMover = jalking.getRequester();
//        }
//
//        LatLonPair targetLocation = notMover.getLatLonPair();
//
//        // req.getTime() 초 만큼 for 문 돌면서 thread sleep 하면서 유저 current 위치 변경
//
//        List<LatLonPair> moveList = interiorDivision(
//                targetLocation.getLat(), targetLocation.getLon(),
//                mover.getLatLonPair().getLat(), mover.getLatLonPair().getLon(),
//                30
//        );
////        while ( time < moveList.size() ){
////            Thread.sleep(1000); // 1초마다 이동한다.
////            LatLonPair latLonPair = latLonPairRepository.save(moveList.get(time));
////            mover.updateMyLocation(latLonPair);
////            time++;
////        }
//        moveList.add(new LatLonPair(targetLocation.getLat(), targetLocation.getLon()));
//        mover.updateMyLocation(new LatLonPair(targetLocation.getLat(), targetLocation.getLon()));
//        end(jId); // jalking 종료되고 mover 가 승리자가 된다.
//        //System.out.println(mover.getWins() + " 이긴 애" + mover.getId());
//        return LatLonPairDto.toDtoList(moveList);
//    }

    @Transactional
    public List<LatLonPairDto> move(Long pvpId) throws InterruptedException {
        Jalking pvp = jalkingRepository.findById(pvpId).orElseThrow(PvpNotFoundException::new);
        Thread.sleep(20000); // 20초 안에 내가 10번 하기
        end(pvpId);
        return LatLonPairDto.toDtoList(new ArrayList<>());
    }

    @Transactional
    public Long updateSeperate(Long pvpId, Long lid) throws InterruptedException {
        LatLonPair latLonPair = latLonPairRepository.findById(lid).orElseThrow(LatLonPairNotFoundException::new);
        Jalking pvp = jalkingRepository.findById(pvpId).orElseThrow(PvpNotFoundException::new);
        Member mover = pvp.getReceiver();
        mover.updateMyLocation(latLonPair);
        memberRepository.latLonPairUpdate(latLonPair, mover.getId());
        return pvpId;
    }
}
