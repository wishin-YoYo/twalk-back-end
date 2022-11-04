package server.twalk.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.Member.entity.Walking;

public interface WalkingRepository extends JpaRepository<Walking, Long> {
}
