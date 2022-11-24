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
import server.twalk.Walking.exception.JalkingNotFoundException;
import server.twalk.Walking.exception.WalkingNotFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response MemberNotFoundExcpetion(MemberNotFoundException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(404, "Member not found.");
    }

    @ExceptionHandler(MemberNotWalkingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response MemberNotWalkingException(MemberNotWalkingException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(400, "Member's location info doesn't exist.");
    }

    @ExceptionHandler(SocketRoomRefreshException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response SocketRoomRefreshException(SocketRoomRefreshException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(404, "Socket Room Expired, Need Refresh");
    }

    @ExceptionHandler(WalkingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response WalkingNotFoundException(WalkingNotFoundException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(404, "Walking Not Found ");
    }

    @ExceptionHandler(JalkingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response JalkingNotFoundException(JalkingNotFoundException e) {
        log.info("e = {}", e.getMessage());
        return Response.failure(404, "Jalking Not Found - maybe Canceled Jalking ");
    }

}
