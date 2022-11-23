package server.twalk.Walking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.twalk.Member.dto.MemberDto;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalkingDto {

    private Long id;
    private LocalDate nowDate;
    private Integer walkingCount;
    private MemberDto user;
    private ArrayList<Double> lat = new ArrayList<>();
    private ArrayList<Double> lon = new ArrayList<>();

}