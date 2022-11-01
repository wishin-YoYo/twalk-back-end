package server.twalk.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.twalk.User.entity.Member;

public interface UserRepository extends JpaRepository<Member, Long> {
}
