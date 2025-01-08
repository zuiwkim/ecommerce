package kr.hhplus.be.server.app.domain.point;

public interface PointRepository {
    Point findByUserId(Long userId);
}
