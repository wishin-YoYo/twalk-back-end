package server.twalk.Walking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.twalk.Member.dto.MemberDto;
import server.twalk.Walking.entity.Walking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalkingDto {

    private Long id;

    private Integer walkingCount;

    private MemberDto user;

    private List<LatLonPairDto> list;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;

    public static WalkingDto toDto(Walking walking){
        return new WalkingDto(
                walking.getId(),
                walking.getWalkingCount(),
                MemberDto.from(walking.getMember()),
                LatLonPairDto.toDtoList(walking.getLatLonPair()),
                walking.getCreatedAt(),
                walking.getModifiedAt()
        );
    }

    public static List<WalkingDto> toDtoList(List<Walking>  walkings){
        return walkings.stream().map(
                walking -> new WalkingDto(
                        walking.getId(),
                        walking.getWalkingCount(),
                        MemberDto.from(walking.getMember()),
                        LatLonPairDto.toDtoList(walking.getLatLonPair()),
                        walking.getCreatedAt(),
                        walking.getModifiedAt()
                )
        ).collect(Collectors.toList());
    }
}