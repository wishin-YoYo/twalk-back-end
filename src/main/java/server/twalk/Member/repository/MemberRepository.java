package server.twalk.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.twalk.Member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(
            "select m from Member m where m.id = (:id)"
    )
    Optional<Member> findByIdCustom(@Param("id")Long id);
}
