package server.twalk.PvP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.twalk.Member.entity.Member;
import server.twalk.PvP.entity.PvpMatch;
import server.twalk.Walking.entity.Jalking;

import java.util.List;

public interface PvpMatchRepository extends JpaRepository<PvpMatch, Long> {
    List<PvpMatch> findByRequesterOrderByCreatedAtDesc(Member member);
    List<PvpMatch> findByReceiverOrderByCreatedAtDesc(Member member);

    @Query(
            "select i from PvpMatch i inner join fetch i.requester where i IN (:pvpMatch) order by i.createdAt desc "
    )
    List<PvpMatch> findByPvpMatches(@Param("pvpMatch") List<PvpMatch> PvpMatches);

}
