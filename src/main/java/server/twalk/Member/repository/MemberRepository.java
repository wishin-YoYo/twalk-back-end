package server.twalk.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.Member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
