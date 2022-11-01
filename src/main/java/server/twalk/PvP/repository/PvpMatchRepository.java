package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.PvP.entity.PvpMatch;

public interface PvpMatchRepository extends JpaRepository<PvpMatch, Long> {
}
