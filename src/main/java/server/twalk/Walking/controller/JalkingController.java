package server.twalk.Walking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.service.JalkingService;

import javax.validation.Valid;

@Api(tags = {"Jalking"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class JalkingController {

    private final JalkingService jalkingService;

    @ApiOperation(value = "jalking 생성", notes = "join walking 객체 생성 \n requesterId, receiverId 보내주세요")
    @PostMapping("/join-walking")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(
            @Valid @ModelAttribute
                    JalkingReq req
    ) {
        return Response.success(
                jalkingService.create(req )
        );
    }

    @ApiOperation(value = "jalking 승인", notes = "join walking 승인")
    @PutMapping("/join-walking/approve/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response approve(
            @PathVariable Long id
    ) {
        return Response.success( // receiver 만 가능
                jalkingService.approve(id)
        );
    }

    @ApiOperation(value = "jalking 거절", notes = "join walking 거절")
    @PutMapping("/join-walking/reject/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response reject(
            @PathVariable Long id
    ) {
        return Response.success( // receiver 만 가능
                jalkingService.rejected(id)
        );
    }

    @ApiOperation(value = "jalking 취소", notes = "join walking 취소 => 삭제됨")
    @PutMapping("/join-walking/cancel/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response cancel(
            @PathVariable Long id
    ) {
        return Response.success(
                jalkingService.cancel(id) // jalking 삭제
        );
    }


    @ApiOperation(value = "jalking 조회", notes = "jalking 조회")
    @GetMapping("/jalking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(
              @PathVariable Long id
    ) {
        return Response.success(jalkingService.read(id));
    }

    @ApiOperation(value = "모든 jalking 조회", notes = "jalkign 조회")
    @GetMapping("/jalkings")
    @ResponseStatus(HttpStatus.OK)
    public Response readAll() {
        return Response.success(jalkingService.readAll());
    }

    @ApiOperation(value = "내가 요청한 jalking request", notes = "내가 요청한 jalking request들 조회 \n requesterId 만 보내주세요")
    @GetMapping("/jalking/request")
    @ResponseStatus(HttpStatus.OK)
    public Response readRequest(JalkingReq req) {
        return Response.success(jalkingService.readRequestJalkings(req));
    }

    @ApiOperation(value = "내게 요청 들어온 jalking", notes = "내게 요청 들어온 jalking 조회 \n receiverId 만 보내주세요")
    @GetMapping("/jalking/receive")
    @ResponseStatus(HttpStatus.OK)
    public Response readReceived(JalkingReq req) {
        return Response.success(jalkingService.readReceivedJalkings(req));
    }
}
