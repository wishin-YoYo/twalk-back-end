package server.twalk.Walking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkingReq {

    private double lat;
    private double lon;
    private Long memberId;

    // end 할 때만 지금까지 누적됐었던 걷기 보내주기
    private double distance;

}
