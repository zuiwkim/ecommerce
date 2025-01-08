package kr.hhplus.be.server.app.domain.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public Point getUserPoint(Long userId) {
        return pointRepository.findByUserId(userId);
    }
}