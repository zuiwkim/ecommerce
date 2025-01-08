package kr.hhplus.be.server.app.infrastructure.point;

import kr.hhplus.be.server.app.domain.point.Point;
import kr.hhplus.be.server.app.domain.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository jpaRepository;

    @Override
    public Point findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId)
                .orElseThrow(() -> new NullPointerException("해당 유저의 포인트를 찾을 수 없습니다."));
    }
}