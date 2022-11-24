package server.twalk.Walking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.twalk.Member.dto.MemberDto;
import server.twalk.Walking.entity.Jalking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JalkingDto {

    Long id;

    MemberDto requester;

    MemberDto receiver;

    String content;

    private List<LatLonPairDto> list;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;

    public static JalkingDto toDto(Jalking jalking){
        return new JalkingDto(
                jalking.getId(),
                MemberDto.from(jalking.getRequester()),
                MemberDto.from(jalking.getReceiver()),
                jalking.getContent(),
                LatLonPairDto.toDtoList(jalking.getLatLonPair()),
                jalking.getCreatedAt(),
                jalking.getModifiedAt()
        );
    }

    public static List<JalkingDto> toDtoList(List<Jalking> jalkings){
        return jalkings.stream().map(
                jalking -> new JalkingDto(
                        jalking.getId(),
                        MemberDto.from(jalking.getRequester()),
                        MemberDto.from(jalking.getReceiver()),
                        jalking.getContent(),
                        LatLonPairDto.toDtoList(jalking.getLatLonPair()),
                        jalking.getCreatedAt(),
                        jalking.getModifiedAt()
                )
        ).collect(Collectors.toList());
    }
}
