package server.twalk.Walking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.dto.request.WalkingReq;
import server.twalk.Walking.service.JalkingService;

import javax.validation.Valid;

@Api(tags = {"Jalking"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class JalkingController {

    private final JalkingService jalkingService;

    @ApiOperation(value = "jalking 생성", notes = "join walking 객체 생성 \n * requesterId, receiverId 보내주세요")
    @PostMapping("/jalking")
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
    @PutMapping("/jalking-approve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response approve(
            @PathVariable Long id
    ) {
        return Response.success( // receiver 만 가능
                jalkingService.approve(id)
        );
    }

    @ApiOperation(value = "jalking 거절", notes = "join walking 거절")
    @PutMapping("/jalking-reject/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response reject(
            @PathVariable Long id
    ) {
        return Response.success( // receiver 만 가능
                jalkingService.rejected(id)
        );
    }

    @ApiOperation(value = "jalking 취소", notes = "join walking 취소 => 삭제됨")
    @PutMapping("/jalking-cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
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

    @ApiOperation(value = "jalking 종료", notes = "jalking 종료 \n * jalkingg id 주시면 됩니다")
    @PutMapping("/jalking/end/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response end(
            @PathVariable Long id
    ) {
        return Response.success(
                jalkingService.end(id)
        );
    }

}
