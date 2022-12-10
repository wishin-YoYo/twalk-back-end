package server.twalk.Walking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.twalk.Walking.entity.LatLonPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WalkingCommonService {

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        // 단위 별로 계산 값 다르게 해주는 과정
        if (Objects.equals(unit, "kilometer")) {
            dist = dist * 1.609344;
        } else if(Objects.equals(unit, "meter")){
            dist = dist * 1609.344;
        }
        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    // 출처 : https://fruitdev.tistory.com/189

    public static List<LatLonPair> interiorDivision(double lat1, double lon1, double lat2, double lon2, int time){
        // m:n 내분
        List<LatLonPair> returnLatLonPair = new ArrayList<>();
        double m = 1;
        double n = time-1;

        while(m<=n) {
            double q = (n * lat1 + m * lat2) / (m + n);
            double r = (n * lon1 + m * lon2) / (m + n);
            System.out.println(q + " " + r + " \n");
            returnLatLonPair.add(new LatLonPair(q,r));
            m++;
        }
        return returnLatLonPair;
    }
}
