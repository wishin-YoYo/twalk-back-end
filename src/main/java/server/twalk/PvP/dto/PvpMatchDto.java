package server.twalk.PvP.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import server.twalk.Member.dto.MemberDto;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.Walking.dto.LatLonPairDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PvpMatchDto {

    Long id;

    MemberDto requester;

    MemberDto receiver;

    String content;

    String pvpMode;

    String status;

    @Nullable
    LatLonPairDto targetLocation;

    @Nullable
    MemberDto winner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;


    public static PvpMatchDto toDto(PvpMatch pvpMatch){
        return new PvpMatchDto(
                pvpMatch.getId(),
                MemberDto.from(pvpMatch.getRequester()),
                MemberDto.from(pvpMatch.getReceiver()),
                pvpMatch.getContent(),
                pvpMatch.getStatus().getStatusType().name(),
                pvpMatch.getPvpMode().getPvpModeType().name(),
                pvpMatch.getTargetLocation()==null?null:LatLonPairDto.toDto(pvpMatch.getTargetLocation()),
                pvpMatch.getWinner()==null?null : MemberDto.from(pvpMatch.getWinner()),
                pvpMatch.getCreatedAt(),
                pvpMatch.getModifiedAt()
        );
    }

    public static List<PvpMatchDto> toDtoList(List<PvpMatch> pvpMatches){
        return pvpMatches.stream().map(
                pvpMatch -> new PvpMatchDto(
                        pvpMatch.getId(),
                        MemberDto.from(pvpMatch.getRequester()),
                        MemberDto.from(pvpMatch.getReceiver()),
                        pvpMatch.getContent(),
                        pvpMatch.getStatus().getStatusType().name(),
                        pvpMatch.getPvpMode().getPvpModeType().name(),
                        pvpMatch.getTargetLocation()==null?null:LatLonPairDto.toDto(pvpMatch.getTargetLocation()),
                        pvpMatch.getWinner()==null?null : MemberDto.from(pvpMatch.getWinner()),
                        pvpMatch.getCreatedAt(),
                        pvpMatch.getModifiedAt()
                )
        ).collect(Collectors.toList());
    }
}
