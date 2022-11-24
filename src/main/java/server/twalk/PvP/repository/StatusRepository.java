package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.PvP.entity.Status;
import server.twalk.PvP.entity.StatusType;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByStatusType(StatusType statusType);
}
