package server.twalk.Walking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.Member.entity.Member;
import server.twalk.Member.entity.Walking;

import java.util.List;

public interface WalkingRepository extends JpaRepository<Walking, Long> {

    List<Walking> findByUserOrderByIdDesc(Member member) ;

}
