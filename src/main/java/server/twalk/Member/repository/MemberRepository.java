package server.twalk.Member.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import server.twalk.Member.entity.Member;
import server.twalk.Walking.entity.LatLonPair;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(
            "select m from Member m where m.id = (:id)"
    )
    Optional<Member> findByIdCustom(@Param("id")Long id);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Member m set m.latLonPair = :l where m.id = :id")
    void latLonPairUpdate(@Param("l") LatLonPair l, @Param("id") Long id);

}
