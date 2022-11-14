package server.twalk.Socket.entity;

import org.springframework.web.socket.WebSocketSession;
import server.twalk.Common.entity.EntityDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;

public class Room extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE2")
    @SequenceGenerator(name="SEQUENCE2", sequenceName="SEQUENCE2", allocationSize=1)
    private Long id;
    private Set<WebSocketSession> sessions = new HashSet<>();
}
