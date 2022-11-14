package server.twalk.Socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import server.twalk.Member.repository.MemberRepository;
import server.twalk.Member.service.MemberService;
import server.twalk.Socket.dto.ChatMessage;
import server.twalk.Socket.dto.RoomDto;
import server.twalk.Socket.exception.SocketRoomRefreshException;
import server.twalk.Socket.service.SocketService;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final SocketService socketService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("socket payload : {}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        RoomDto room = socketService.findRoomById(chatMessage.getRoomId()).orElseThrow(SocketRoomRefreshException::new);
        room.handleTempActions(session, chatMessage, socketService, memberRepository, memberService);

    }

}

