package kr.hhplus.be.server.app.infrastructure.point;

import kr.hhplus.be.server.app.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByUserId(Long userId);
}