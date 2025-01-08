package kr.hhplus.be.server.app.application.point;

import kr.hhplus.be.server.app.domain.point.Point;
import kr.hhplus.be.server.app.domain.point.PointService;
import kr.hhplus.be.server.app.domain.user.UserService;
import kr.hhplus.be.server.app.domain.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PointFacade {

    private final PointService pointService;
    private final UserService userService;

    public GetUserAmountResult getUserPoint(Long userId) {
        Users user = userService.getUser(userId);
        Point point = pointService.getUserPoint(user.getId());

        return new GetUserAmountResult(user.getId(), user.getUserMail(), point.getUserAmount());
    }
}