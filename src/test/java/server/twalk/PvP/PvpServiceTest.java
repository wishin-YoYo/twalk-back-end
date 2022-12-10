package server.twalk.PvP;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.dto.PvpMoveReq;
import server.twalk.PvP.entity.*;
import server.twalk.PvP.repository.PvpMatchRepository;
import server.twalk.PvP.repository.PvpModeRepository;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.PvP.service.PvPService;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.exception.StatusNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PvpServiceTest {

    @InjectMocks
    PvPService pvpService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PvpMatchRepository pvpMatchRepository;
    @Mock
    StatusRepository statusRepository;
    @Mock
    PvpModeRepository pvpModeRepository;

    @Test
    @Transactional
    void moveTest() throws InterruptedException {
        // given
        Member member1 = createMember1();
        Member member3 = createMember3();
        PvpMatch pvpMatch = createIFCPvp();

        given(pvpMatchRepository.findById(anyLong())).willReturn(Optional.of(pvpMatch));
        given(statusRepository.findByStatusType(StatusType.COMPLETE)).willReturn(Optional.of(createStatusCOMPLETE()));

        //when
        Long winnerId = pvpService.move(pvpMatch.getId(), new PvpMoveReq(10));

        assertEquals(3L, winnerId);
    }

    private Member createMember3() {
        return new Member(
                3L,
                "password",
                "username",
                true,
                true,
                "hi",
                0,
                0,
                0,
                0,
                new LatLonPair(37.5317421, 126.92774130000001 )
        );
    }

    private Member createMember1() {
        return new Member(
                1L,
                "password",
                "username",
                true,
                true,
                "hi",
                0,
                0,
                0,
                0,
                new LatLonPair(37.5317421, 126.92774130000001 )
        );
    }

    private Status createStatusONGOING() {
        return new Status (
                1L,
                StatusType.ONGOING
        );
    }

    private Status createStatusCOMPLETE() {
        return new Status (
                2L,
                StatusType.COMPLETE
        );
    }



    private PvpMatch createIFCPvp() {
        return new PvpMatch(
            1L,
               pvpModeRepository.findByPvpModeType(PvpModeType.IFC),
                createMember1(),
                createMember3(),
                null,
                " " ,
                createStatusONGOING(),
                new LatLonPair(37.526001, 126.925215),
                0
        );
    }
}
