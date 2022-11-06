package server.twalk.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.twalk.Member.entity.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String name;
    private boolean activated;
    private boolean showLocation;
    private String comment;
    private Long totalDistance;
    private Long totalCalories;
    private Integer wins;
    private Integer loses;
    private String winning_rate;

    public static MemberDto from(Member member){
        return MemberDto.builder()

                .id(member.getId())
                .name(member.getName())
                .activated(member.isActivated())
                .showLocation(member.isShowLocation())
                .comment(member.getComment())
                .totalDistance(member.getTotalDistance())
                .totalCalories(member.getTotalCalories())
                .wins(member.getWins())
                .loses(member.getLoses())
                .winning_rate(
                        String.format(
                        "%.3f", member.getWins()/(member.getWins()+member.getLoses())
                        )
                )
                .build();
    }

}
