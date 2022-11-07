package server.twalk.Member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.Member.service.MemberService;

@Api(tags = {"Member"})
@RestController
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = "")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "show 값 선택", notes = "사용자 위치 보여주기(show) on/off 선택")
    @PostMapping("/show/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response show(
            @PathVariable Long id
    ) {

        return Response.success(
                memberService.show(id)
        );
    }

    @ApiOperation(value = "activate 값 선택", notes = "사용자 활동 상태(activate) on/off 선택")
    @PostMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response activate(
            @PathVariable Long id
    ) {
        return Response.success(
                memberService.activated(id)
        );
    }

    @ApiOperation(value = "모든 멤버 get", notes = "모든 사용자 데려오기")
    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public Response readAll() {
        return Response.success(memberService.readAll());
    }


}
