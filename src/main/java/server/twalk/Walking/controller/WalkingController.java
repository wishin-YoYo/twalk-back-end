package server.twalk.Walking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.Walking.dto.request.WalkingReq;
import server.twalk.Walking.service.WalkingService;

import javax.validation.Valid;

@Api(tags = {"Walking"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class WalkingController {
    private final WalkingService walkingService;

    @ApiOperation(value = "사용자 홀로 walking 생성", notes = "사용자 걷기 객체 생성")
    @PostMapping("/walking")
    @ResponseStatus(HttpStatus.CREATED)
    public Response show(
            @Valid @ModelAttribute
                    WalkingReq req
    ) {
        return Response.success(
                walkingService.create(req )
        );
    }

    @ApiOperation(value = "사용자 걷기 update", notes = "사용자 걷기 위도, 경도 업데이트")
    @PostMapping("/walking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @Valid @ModelAttribute
                    WalkingReq req
    ) {
        return Response.success(
                walkingService.update(req, id)
        );
    }

    @ApiOperation(value = "사용자 걷기 종료", notes = "사용자 걷기 종료")
    @PostMapping("/walking/end/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response end(
            @PathVariable Long id,
            @Valid @ModelAttribute
                    WalkingReq req
    ) {
        return Response.success(
                walkingService.end(req, id)
        );
    }

}
