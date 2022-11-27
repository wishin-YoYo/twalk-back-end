package server.twalk.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.twalk.Member.entity.Member;
import server.twalk.Walking.dto.LatLonPairDto;

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
    private double totalDistance;
    private double totalCalories;
    private Integer wins;
    private Integer loses;
    private String winning_rate; //전적
    private LatLonPairDto latLonPairDto;

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
                        member.getWins()+member.getLoses()==0?
                                "0"
                                :
                        String.format(
                        "%.3f", member.getWins()/(member.getWins()+member.getLoses())
                        )
                )
                .latLonPairDto(LatLonPairDto.toDto(member.getLatLonPair()))
                .build();
    }

}
