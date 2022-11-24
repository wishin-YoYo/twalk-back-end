package server.twalk.PvP.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.service.PvPService;

import javax.validation.Valid;

@Api(tags = {"PvP"})
@RequiredArgsConstructor
@RestController
@Slf4j
public class PvpController {

    private final PvPService pvpService;

    @ApiOperation(value = "pvp 생성", notes = "pvp 객체 생성 \n * requesterId, receiverId, pvpMode(\"CHASE\" 혹은 \"RUN\" 중 하나) 보내주세요")
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

    @ApiOperation(value = "내가 요청한 pvp request", notes = "내가 요청한 pvp request들 조회 \n * requesterId 만 보내주세요")
    @GetMapping("/pvp/request")
    @ResponseStatus(HttpStatus.OK)
    public Response readRequest(
            @Valid @ModelAttribute
                    PvpReq req
    ) {
        return Response.success(pvpService.readRequestPvp(req));
    }

    @ApiOperation(value = "내게 요청 들어온 pvp", notes = "내게 요청 들어온 pvp 조회 \n * receiverId 만 보내주세요")
    @GetMapping("/pvp/receive")
    @ResponseStatus(HttpStatus.OK)
    public Response readReceived(
            @Valid @ModelAttribute
                    PvpReq req
    ) {
        return Response.success(pvpService.readReceivedPvps(req));
    }


}
