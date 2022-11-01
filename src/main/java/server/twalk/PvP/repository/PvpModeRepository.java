package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.PvP.entity.PvpMode;

public interface PvpModeRepository extends JpaRepository<PvpMode, Long> {
}
