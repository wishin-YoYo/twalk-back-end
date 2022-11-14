package server.twalk.Common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import server.twalk.Common.entity.response.Response;
import server.twalk.Member.exception.MemberNotFoundException;
import server.twalk.Member.exception.MemberNotWalkingException;
import server.twalk.Socket.exception.SocketRoomRefreshException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response MemberNotFoundExcpetion(MemberNotFoundException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(404, "멤버가 존재하지 않습니다.");
    }

    @ExceptionHandler(MemberNotWalkingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response MemberNotWalkingException(MemberNotWalkingException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(400, "멤버의 위치 정보가 존재하지 않습니다.");
    }

    @ExceptionHandler(SocketRoomRefreshException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response SocketRoomRefreshException(SocketRoomRefreshException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(404, "Socket Room Expired, Need Refresh");
    }

}
