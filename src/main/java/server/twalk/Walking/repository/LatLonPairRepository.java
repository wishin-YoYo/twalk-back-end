package server.twalk.Walking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import server.twalk.Walking.entity.LatLonPair;

public interface LatLonPairRepository extends JpaRepository<LatLonPair, Long> {

}
