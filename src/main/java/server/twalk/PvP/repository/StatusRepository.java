package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.PvP.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
