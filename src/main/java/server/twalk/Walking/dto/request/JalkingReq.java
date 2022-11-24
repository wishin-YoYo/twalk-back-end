package server.twalk.Walking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JalkingReq { // jalking 초기 생성 & 멤버에게 신청

    Long requesterId;
    Long receiverId;
    Long jalkingId;

}
