package server.twalk.Walking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.twalk.Walking.entity.LatLonPair;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatLonPairDto {
    double lat;
    double lon;

    public static List<LatLonPairDto> toDtoList (List<LatLonPair> list){
        return list.stream().map(
                latLonPair -> new LatLonPairDto(
                        latLonPair.getLat(),
                        latLonPair.getLon()
                )
        ).collect(Collectors.toList());
    }

}
