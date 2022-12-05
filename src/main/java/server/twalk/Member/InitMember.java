package server.twalk.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.entity.PvpModeType;
import server.twalk.PvP.repository.PvpModeRepository;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.exception.LatLonPairNotFoundException;
import server.twalk.Walking.repository.LatLonPairRepository;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class InitMember {

    private final MemberRepository memberRepository;
    private final LatLonPairRepository latLonPairRepository;
    private final PvpModeRepository pvpModeRepository;

    public void initSogang() {
        initSogangMember();
        initTargetSogang();
    }

    public void initIFC() {
        initIFCMember();
        initTargetIFC();
    }

    @Transactional
    private void initSogangMember() {

        //서강대 : 37.550897 126.940976

        match(1L, 10000000001L);
        match(2L, 10000000002L);
        match(3L, 10000000003L);
        match(4L, 10000000004L);
        match(5L, 10000000005L);
        match(6L, 10000000006L);
    }

    @Transactional
    private void initTargetSogang() {

        pvpModeRepository.findByPvpModeType(PvpModeType.IFC).setTargetLocation(false);
        pvpModeRepository.findByPvpModeType(PvpModeType.SOGANG).setTargetLocation(true);
        pvpModeRepository.findByPvpModeType(PvpModeType.FLAG).setTargetLocation(false);
    }

    @Transactional
    private void initIFCMember() {

        //ifc 37.526001 126.925215

        match(1L, 20000000001L);
        match(2L, 20000000002L);
        match(3L, 20000000003L);
        match(4L, 20000000004L);
        match(5L, 20000000005L);
        match(6L, 20000000006L);
    }

    @Transactional
    private void initTargetIFC() {

        pvpModeRepository.findByPvpModeType(PvpModeType.IFC).setTargetLocation(true);
        pvpModeRepository.findByPvpModeType(PvpModeType.SOGANG).setTargetLocation(false);
        pvpModeRepository.findByPvpModeType(PvpModeType.FLAG).setTargetLocation(false);

    }

    @Transactional
    void match(Long memberId, Long lId){

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        LatLonPair latLonPair = latLonPairRepository.findById(lId).orElseThrow(LatLonPairNotFoundException::new);
        member.updateMyLocation(latLonPair);

    }

}

