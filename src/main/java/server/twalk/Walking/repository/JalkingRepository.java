package server.twalk.Walking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.twalk.Member.entity.Member;
import server.twalk.Walking.entity.Jalking;

import java.util.List;

public interface JalkingRepository extends JpaRepository<Jalking , Long> {
    List<Jalking> findByRequesterOrderByCreatedAtDesc(Member member);
    List<Jalking> findByReceiverOrderByCreatedAtDesc(Member member);
    @Query(
            "select i from Jalking " +
                    "i where i IN (:jalking) order by i.createdAt desc"
    )
    List<Jalking> findByJalkings(@Param("jalkings") List<Jalking> jalkings);

}
