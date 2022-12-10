package server.twalk.Walking;

import org.junit.jupiter.api.Test;
import server.twalk.Walking.entity.LatLonPair;
import server.twalk.Walking.service.WalkingCommonService;

import java.util.List;

public class WalkingCommonServiceTest {

    @Test
    public void 유저_위도_경도_이동체크_case1() throws Exception {
        //given
        List<LatLonPair> latLonPairList = WalkingCommonService.interiorDivision
                (37.53238, 126.928022, 37.526001, 126.925215, 10);

        for(LatLonPair latLonPair : latLonPairList){
            System.out.println(latLonPair.getLat() + "  " + latLonPair.getLon() + "\n");
        }

    }


    @Test
    public void 유저_위도_경도_이동체크_case2() throws Exception {
        //given
        List<LatLonPair> latLonPairList = WalkingCommonService.interiorDivision
                (37.53238, 126.98822, 37.526001, 126.925215, 10);

        for(LatLonPair latLonPair : latLonPairList){
            System.out.println(latLonPair.getLat() + "  " + latLonPair.getLon() + "\n");
        }

    }


    @Test
    public void 유저_위도_경도_이동체크_case3() throws Exception {
        //given
        List<LatLonPair> latLonPairList = WalkingCommonService.interiorDivision
                (37.53238, 126.9332322, 37.526001, 126.925215, 10);

        for(LatLonPair latLonPair : latLonPairList){
            System.out.println(latLonPair.getLat() + "  " + latLonPair.getLon() + "\n");
        }

    }

    @Test
    public void 유저_위도_경도_이동체크_case4() throws Exception {
        //given
        List<LatLonPair> latLonPairList = WalkingCommonService.interiorDivision
                (37.53238, 126.924122, 37.526001, 126.925215, 10);

        for(LatLonPair latLonPair : latLonPairList){
            System.out.println(latLonPair.getLat() + "  " + latLonPair.getLon() + "\n");
        }

    }

    @Test
    public void 유저_위도_경도_이동체크_case5() throws Exception {
        //given
        List<LatLonPair> latLonPairList = WalkingCommonService.interiorDivision
                (37.53238, 126.923422, 37.526001, 126.925215, 10);

        for(LatLonPair latLonPair : latLonPairList){
            System.out.println(latLonPair.getLat() + "  " + latLonPair.getLon() + "\n");
        }

    }

}
