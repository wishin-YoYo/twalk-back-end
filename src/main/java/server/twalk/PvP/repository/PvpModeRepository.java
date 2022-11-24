package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.PvP.entity.PvpMode;
import server.twalk.PvP.entity.PvpModeType;

public interface PvpModeRepository extends JpaRepository<PvpMode, Long> {
    PvpMode findByPvpModeType(PvpModeType pvpModeType);
}
