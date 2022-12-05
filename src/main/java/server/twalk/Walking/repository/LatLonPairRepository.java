package server.twalk.Walking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.Walking.entity.LatLonPair;

public interface LatLonPairRepository extends JpaRepository<LatLonPair, Long> {
}
