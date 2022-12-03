package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.Member.entity.Member;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.Walking.entity.Jalking;

import java.util.List;

public interface PvpMatchRepository extends JpaRepository<PvpMatch, Long> {
    List<PvpMatch> findByRequesterOrderByCreatedAt(Member member);
    List<PvpMatch> findByReceiverOrderByCreatedAt(Member member);
}
