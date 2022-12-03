package server.twalk.Walking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.Member.entity.Member;
import server.twalk.Walking.entity.Jalking;

import java.util.List;

public interface JalkingRepository extends JpaRepository<Jalking , Long> {
    List<Jalking> findByRequester(Member member);
    List<Jalking> findByReceiver(Member member);
    List<Jalking> findByRequesterOrderByCreatedAtDesc(Member member);
    List<Jalking> findByReceiverOrderByCreatedAtDesc(Member member);
}
