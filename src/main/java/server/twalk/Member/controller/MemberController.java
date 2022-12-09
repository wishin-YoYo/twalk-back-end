package server.twalk.Member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.Member.service.MemberService;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.Walking.dto.request.JalkingReq;
import server.twalk.Walking.dto.request.WalkingReq;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = {"Member"})
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "show 값 선택", notes = "사용자 위치 보여주기(show) on/off 선택 \n  * url param : member id 를 주시면 됩니다")
    @PostMapping("/show/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response show(
            @PathVariable Long id
    ) {
        return Response.success(
                memberService.show(id)
        );
    }

    @ApiOperation(value = "activate 값 선택", notes = "사용자 활동 상태(activate) on/off 선택 \n  * url param : member id 를 주시면 됩니다")
    @PostMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response activate(
            @PathVariable Long id
    ) {
        return Response.success(
                memberService.activated(id)
        );
    }

    @ApiOperation(value = "한 명 멤버 get", notes = "한 명 데려오기 \n  * url param : member id 를 주시면 됩니다")
    @GetMapping("/member/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read (
            @PathVariable Long id
    ) {
        return Response.success(memberService.read(id));
    }

    @ApiOperation(value = "모든 멤버 get", notes = "모든 사용자 데려오기")
    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public Response readAll() {
        return Response.success(memberService.readAll());
    }

    // 내 주변 사용자 데려오기
    @ApiOperation(value = "주변 걷기 사용자 get ", notes = "주변에 함께 걸을 수 있는 후보의 사람들 목록 데려오기 \n  * url param : member id 를 주시면 됩니다")
    @GetMapping("/member-around/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readAroundMembers(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readAround(id));
    }

    // 내 현재 위치 데려오기
    @ApiOperation(value = "내 위치 get ", notes = "내 위치 데려오기 \n * url param : member id 를 주시면 됩니다")
    @GetMapping("/member-location/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response myLocation(
            @PathVariable Long id
    ) {
        return Response.success(memberService.myLocation(id));
    }

    // 내 현재 위치 갱신
    @ApiOperation(value = "내 현재 위치 update ", notes = "내 현재 위치 표시하기 \n * url param : member id 를 넣어 주시면 됩니다. \n lat, lon 만 주시면 됩니다 ")
    @PutMapping("/member-location/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateMyLocation(
            @PathVariable Long id,
            @Valid @ModelAttribute
                    WalkingReq req
    ) {
        return Response.success(memberService.updateMyLocation(id, req));
    }

    @ApiOperation(value = "내 모든 걷기 기록 데리고오기", notes = "내 모든 걷기 기록 데리고오기 \n *  url param : member id 주시면 됩니다")
    @GetMapping("/member-history/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readMineAll(
            @PathVariable Long id
    ) {
        return Response.success(
                memberService.readMineAll(id)
        );
    }


    @ApiOperation(value = "내가 요청한 jalking request", notes = "내가 요청한 jalking request들 조회\n *  url param : member id 주시면 됩니다 ")
    @GetMapping("/jalking-req/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readRequest(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readRequestJalkings(id));
    }

    @ApiOperation(value = "내게 요청 들어온 jalking", notes = "내게 요청 들어온 jalking 조회\n *  url param : member id 주시면 됩니다 ")
    @GetMapping("/jalking-rec/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readReceived(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readReceivedJalkings(id));
    }


    @ApiOperation(value = "내가 요청한 pvp request", notes = "내가 요청한 pvp request들 조회\n *  url param : member id 주시면 됩니다")
    @GetMapping("/pvp-req/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readPVPRequest(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readRequestPvp(id));
    }

    @ApiOperation(value = "내게 요청 들어온 pvp", notes = "내게 요청 들어온 pvp 조회\n *  url param : member id 주시면 됩니다")
    @GetMapping("/pvp-rec/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response readPVPReceived(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readReceivedPvps(id));
    }
    @ApiIgnore
    @PostMapping("/sogang")
    @ResponseStatus(HttpStatus.OK)
    public Response memberSogang(
    ) {
        memberService.memberSogang();
        return Response.success();
    }
    @ApiIgnore
    @PostMapping("/ifc")
    @ResponseStatus(HttpStatus.OK)
    public Response memberIFC(
    ) {
        memberService.memberIFC();
        return Response.success();
    }

    @ApiOperation(value = "내 jalking 기록 조회", notes = "내 jalking 중 완료된 것 리스트")
    @GetMapping("/jalking/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response myJalking(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readMyJalking(id));
    }

    @ApiOperation(value = "내 pvp 기록 조회", notes = "내 pvp 중 완료된 것 리스트")
    @GetMapping("/pvp/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response myPvp(
            @PathVariable Long id
    ) {
        return Response.success(memberService.readMyPvp(id));
    }

}
