package server.twalk.PvP.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.entity.StatusType;
import server.twalk.PvP.service.PvPService;
import server.twalk.Walking.entity.Jalking;
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.StatusNotFoundException;

import javax.validation.Valid;

@Api(tags = {"PvP"})
@RequiredArgsConstructor
@RestController
@Slf4j
public class PvpController {

    private final PvPService pvpService;

    @ApiOperation(value = "pvp 생성", notes = "pvp 객체 생성 \n * requesterId, receiverId 보내주세요")
    @PostMapping("/pvp")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(
            @Valid @ModelAttribute
                    PvpReq req
    ) {
        return Response.success(
                pvpService.create(req)
        );
    }

    // pvp api 구현하기

    @ApiOperation(value = "pvp 승인", notes = "join walking 승인")
    @PutMapping("/pvp-approve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response approve(
            @PathVariable Long id
    ) {
        return Response.success( // receiver 만 가능
                pvpService.approve(id)
        );
    }

    @ApiOperation(value = "pvp 거절", notes = "pvp 거절")
    @PutMapping("/pvp-reject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response reject(
            @PathVariable Long id
    ) {
        return Response.success( // receiver 만 가능
                pvpService.rejected(id)
        );
    }

    @ApiOperation(value = "pvp 취소", notes = "pvp 취소 => 삭제됨")
    @PutMapping("/pvp-cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response cancel(
            @PathVariable Long id
    ) {
        return Response.success(
                pvpService.cancel(id) // pvp 삭제
        );
    }


    @ApiOperation(value = "pvp 조회", notes = "pvp 조회")
    @GetMapping("/pvp/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(
            @PathVariable Long id
    ) {
        return Response.success(pvpService.read(id));
    }

    @ApiOperation(value = "pvp 목표 위치 설정", notes = "pvp 목표 위치 설정 \n url param 으로 pvpId, body에 lat(위도), lon(경도)만 주시면 됩니다. ")
    @PutMapping("/pvp/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response location(
            @PathVariable Long id,
            @Valid @ModelAttribute
                    PvpReq req
    ) {
        return Response.success(pvpService.setLocation(id, req));
    }

    // pvp 종료
    @ApiOperation(value = "pvp 종료", notes = "pvp 종료 \n url param 으로 pvpId, WINNER member 아이디 보내주시면 됩니다. ")
    @PutMapping("/pvp/end/{id}/{winnerId}")
    @ResponseStatus(HttpStatus.OK)
    public Response location(
            @PathVariable Long id,
            @PathVariable Long winnerId
    ) {
        return Response.success(pvpService.end(id, winnerId));
    }

//    1. 사람 움직여주는 api (id, 시작 좌표, 종료 좌표, 이동 시간)
//    1. 시연 때 API
//    2. 1번(건) - 3번(동윤 API)
//    3. 대전 기록이
//2. pvp 기록 읽어오기, pvp 는 항상 id 1번이 아닌 아이가 이기게 조작하자.
//
//    ⇒ pvp targetLocation 에 다다르면


//    // pvp 종료
//    @ApiOperation(value = "pvp 종료", notes = "pvp 종료 \n url param 으로 pvpId, WINNER member 아이디 보내주시면 됩니다. ")
//    @PutMapping("/pvp/update/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Response location(
//            @PathVariable Long id,
//            @PathVariable Long winnerId
//    ) {
//        return Response.success(pvpService.end(id, winnerId));
//    }
}
