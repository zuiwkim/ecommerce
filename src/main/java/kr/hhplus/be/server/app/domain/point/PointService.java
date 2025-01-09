package kr.hhplus.be.server.app.domain.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PointService {

    private static final int MAX_CHARGE_POINT = 10000000;
    private static final int MIN_CHARGE_POINT = 10;

    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public Point getUserPoint(Long userId) {
        return pointRepository.findByUserId(userId);
    }

    @Transactional
    public Point chargeUserPoint(Long id, Long chargePoint) {
        if (chargePoint > MAX_CHARGE_POINT){
            throw new IllegalArgumentException("최대 충전금액 1000만원을 초과하였습니다!");
        }
        if (chargePoint < MIN_CHARGE_POINT){
            throw new IllegalArgumentException("최소 충전금액 10원 미만입니다!");
        }

        Point point = pointRepository.findByUserId(id);
        point.chargeUserPoint(chargePoint);

        return point;
    }
}