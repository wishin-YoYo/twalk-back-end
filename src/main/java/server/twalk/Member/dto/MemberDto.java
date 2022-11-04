package server.twalk.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private String email;
    private String password;
    private String name;
    private boolean activated;
    private boolean showLocation;
    private String comment;
    private Long totalDistance;
    private Long totalCalories;
    private Integer wins;
    private Integer loses;

}
