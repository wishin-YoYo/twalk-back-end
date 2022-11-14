package server.twalk.Socket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import server.twalk.Socket.dto.RoomDto;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class SocketService {

    private final ObjectMapper objectMapper;

    private Map<String, RoomDto> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<RoomDto> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public Optional< RoomDto> findRoomById(String roomId) {
        return Optional.ofNullable(chatRooms.get(roomId));
    }
    @Transactional
    public RoomDto createRoom() {
        String randomId = UUID.randomUUID().toString();

        RoomDto chatRoom = RoomDto.builder()
                .roomId(randomId)
                .build();
        chatRooms.put(randomId, chatRoom);

        return chatRoom;
    }
    @Transactional
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
