package server.twalk.PvP.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.PvP.dto.PvpMoveReq;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.service.PvPService;

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

    // pvp 이동
    @ApiOperation(value = "pvp 이동 ", notes = "pvp 이동 \n id 에는 pvp id, body 에는 time(초 기준) 보내주시면 됩니다. ")
    @PutMapping("/pvp/move/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response move(
            @PathVariable Long id,
            @Valid @ModelAttribute PvpMoveReq req
    ) throws InterruptedException {
        pvpService.move(id, req);
        return Response.success();
    }

}
