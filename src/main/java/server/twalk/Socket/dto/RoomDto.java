package server.twalk.Socket.dto;


import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;
import server.twalk.Member.entity.Member;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.Member.service.MemberService;
import server.twalk.Socket.service.SocketService;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Transactional
public class RoomDto {

    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public RoomDto(String roomId) {
        this.roomId = roomId;
    }

    @Transactional
    public void handleTempActions(
            @Nullable WebSocketSession session,
            @Nullable ChatMessage chatMessage,
            SocketService socketService,
            MemberRepository memberRepository,
            MemberService memberService
    ) throws ParseException, InterruptedException {


        if (chatMessage != null && chatMessage.getMemberId() != null) {
            Member member = memberRepository.findById(chatMessage.getMemberId()).orElseThrow(MemberNotFoundException::new);
        }

        if (session != null) {
            sessions.add(session);
        }

        // 메시지 제작 - 내 주변에 있는 멤버 리스트 json 형태 문자열로 변환해서 줌
        Gson gson = new Gson();

        int time = 0;
        while(time < 10) {
            chatMessage.setMessage(
                    gson.toJson(memberService.readAround(chatMessage.getMemberId()))
            );
            sendMessage(chatMessage, socketService);
            TimeUnit.SECONDS.sleep(3);
            time+=1;
        }

    }

    @Transactional
    public <T> void sendMessage(T message, SocketService socketService) {
        sessions.parallelStream().forEach(session -> socketService.sendMessage(session, message));
    }

}
