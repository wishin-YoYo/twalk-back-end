package server.twalk.Member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.twalk.Common.entity.response.Response;
import server.twalk.Member.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = "")
public class MemberController {
    private final MemberService memberService;

    // 사용자 location show or not
    //@CrossOrigin(origins = "")
    @PostMapping("/show/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response show(
            @PathVariable Long id
    ) {

        return Response.success(
                memberService.show(id)
        );
    }

    @PostMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response activate(
            @PathVariable Long id
    ) {
        return Response.success(
                memberService.activated(id)
        );
    }



}
