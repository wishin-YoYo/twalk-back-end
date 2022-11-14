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

    // 내 주변 사용자 데려오기
    @ApiOperation(value = "주변 걷기 사용자 get ", notes = "주변에 함께 걸을 수 있는 후보의 사람들 목록 데려오기")
    @GetMapping("/member-around/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readAroundMembers(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readAround(id));
    }

}
