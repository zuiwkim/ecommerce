package kr.hhplus.be.server.app.infrastructure.user;

import kr.hhplus.be.server.app.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);
}
