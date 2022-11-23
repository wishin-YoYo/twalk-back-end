package server.twalk.Walking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LatLonPair {

    private List<Double> lat = new ArrayList<>();
    private List<Double> lon = new ArrayList<>();

    public void addLatLon(double lat, double lon){
        this.lat.add(lat);
        this.lon.add(lon);
    }
}