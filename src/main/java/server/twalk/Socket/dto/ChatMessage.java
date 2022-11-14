package server.twalk.Socket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String roomId; // 방번호
    private String message; // 메시지
    private Long memberId;

    public ChatMessage(String roomId, Long memberId) {
        this.roomId = roomId;
        this.memberId = memberId;
    }

}
