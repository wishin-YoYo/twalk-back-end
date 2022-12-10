package server.twalk.PvP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.twalk.Member.entity.Member;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.PvP.controller.PvpController;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.PvP.entity.PvpModeType;
import server.twalk.PvP.entity.Status;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.repository.PvpMatchRepository;
import server.twalk.PvP.repository.PvpModeRepository;
import server.twalk.PvP.repository.StatusRepository;
import server.twalk.PvP.service.PvPService;
import server.twalk.Walking.entity.LatLonPair;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LatencyTest{
    @InjectMocks
    PvpController pvpController;
    @Mock
    PvPService pvPService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PvpMatchRepository pvpMatchRepository;
    @Mock
    StatusRepository statusRepository;
    @Mock
    PvpModeRepository pvpModeRepository;
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(pvpController).build();
    }


    @Test
    private void pvp_수락() throws Exception {
        // when, then
        mockMvc.perform(
                        multipart("/pvp-approve/{id}", (long) (Math.random() * 1000) +1)
                                .with(requestPostProcessor -> {
                                    requestPostProcessor.setMethod("PUT");
                                    return requestPostProcessor;
                                }))
                .andExpect(status().isOk());
    }


    @Test
    void 멤버_100명_응답_지연_테스트() throws InterruptedException {

        // given

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        // 멤버 생성
        for(int i = 0; i < 100 ; i++){
            Member member = memberRepository.save(createMember((long)i));
            PvpMatch pvpMatch = createPvp((long) (Math.random() * 100) +1, (long) (Math.random() * 100) +1);
        }

        // PARAMETER 로 넘어온 PEOPLE 수만큼 쓰레드 생성
        List<Thread> workers = Stream
                .generate(Thread::new)
                .limit(1000)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);

        for (int i = 1; i <= 1000; i++) {
            executorService.execute(() -> {
                try {
                    pvp_수락();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

    }

    @Test
    void 멤버_1000명_응답_지연_테스트() throws InterruptedException {

        // given

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        // 멤버 생성
        for(int i = 0; i < 1000 ; i++){
            Member member = memberRepository.save(createMember((long)i));
            PvpMatch pvpMatch = createPvp((long) (Math.random() * 1000) +1, (long) (Math.random() * 1000) +1);
        }

        // PARAMETER 로 넘어온 PEOPLE 수만큼 쓰레드 생성
        List<Thread> workers = Stream
                .generate(Thread::new)
                .limit(1000)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);

        for (int i = 1; i <= 1000; i++) {
            executorService.execute(() -> {
                try {
                    pvp_수락();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

    }

    @Test
    void 멤버_10000명_응답_지연_테스트() throws InterruptedException {

        // given

        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        // 멤버 생성
        for(int i = 0; i < 10000 ; i++){
            Member member = memberRepository.save(createMember((long)i));
            PvpMatch pvpMatch = createPvp((long) (Math.random() * 10000) +1, (long) (Math.random() * 10000) +1);
        }

        // PARAMETER 로 넘어온 PEOPLE 수만큼 쓰레드 생성
        List<Thread> workers = Stream
                .generate(Thread::new)
                .limit(10000)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);

        for (int i = 1; i <= 10000; i++) {
            executorService.execute(() -> {
                try {
                    pvp_수락();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

    }


    @Test
    void 멤버_100000명_응답_지연_테스트() throws InterruptedException {

        // given

        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        // 멤버 생성
        for(int i = 0; i < 10000 ; i++){
            Member member = memberRepository.save(createMember((long)i));
            PvpMatch pvpMatch = createPvp((long) (Math.random() * 10000) +1, (long) (Math.random() * 10000) +1);
        }

        // PARAMETER 로 넘어온 PEOPLE 수만큼 쓰레드 생성
        List<Thread> workers = Stream
                .generate(Thread::new)
                .limit(10000)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);

        for (int i = 1; i <= 10000; i++) {
            executorService.execute(() -> {
                try {
                    pvp_수락();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

    }

    public Member createMember(Long id) {

            return new Member(
                    id,
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

    private PvpMatch createPvp(Long id1, Long id2) {
        return new PvpMatch(
                1L,
                pvpModeRepository.findByPvpModeType(PvpModeType.IFC),
                createMember(id1),
                createMember(id2),
                null,
                " " ,
                createStatusONGOING(),
                new LatLonPair(37.526001, 126.925215),
                0
        );
    }

}
