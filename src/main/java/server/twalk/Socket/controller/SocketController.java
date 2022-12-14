package server.twalk.Socket.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.twalk.Socket.dto.RoomDto;
import server.twalk.Socket.service.SocketService;

@Api(tags = {"Socket"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class SocketController {

    private final SocketService socketService;

    @ApiOperation(value = "socket room 생성", notes = "메시지 방 생성")
    @PostMapping
    public RoomDto createRoom() {
        return socketService.createRoom();
    }

}
