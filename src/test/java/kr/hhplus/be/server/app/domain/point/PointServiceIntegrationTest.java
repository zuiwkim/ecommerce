package kr.hhplus.be.server.app.domain.point;

import kr.hhplus.be.server.IntegrationTest;
import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.app.domain.user.UserRepository;
import kr.hhplus.be.server.app.domain.user.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class PointServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointService pointService;

    @Nested
    @DisplayName("포인트 충전 통합 테스트")
    class PointChargeTests {

        @Test
        @DisplayName("[성공케이스] 유저의 포인트가 1000원이 있을경우, 500원 충전시 1500원의 잔액이 남는 테스트")
        void userChargeAmountSuccessTest() {
            // given
            long userId = 1L;
            long userAmount = 1000L;
            long chargeAmount = 500L;
            Users user = new Users(null, "aehdals9900@gmail.com", "천동민", LocalDateTime.now(), LocalDateTime.now(), false);
            Point point = new Point(null, userId, userAmount, LocalDateTime.now(), LocalDateTime.now(), false);

            // when
            userRepository.save(user);
            pointRepository.save(point);
            Point result = pointService.chargeUserPoint(user.getId(), chargeAmount);

            // then
            assertNotNull(result);
            assertEquals(1500L, result.getUserAmount());

            // DB에 실제로 저장되었는지 확인
            Point savedPoint = pointRepository.findByUserId(user.getId());
            assertEquals(1500L, savedPoint.getUserAmount());
        }
    }

    @Nested
    @DisplayName("포인트 조회 통합 테스트")
    class PointGetTests {

        @Test
        @DisplayName("[성공케이스] 유저의 포인트 조회 성공")
        void getUserPointSuccessTest() {
            // given
            long userId = 1L;
            long userAmount = 1000L;
            Users user = new Users(null, "aehdals9900@gmail.com", "천동민", LocalDateTime.now(), LocalDateTime.now(), false);
            Point point = new Point(null, userId, userAmount, LocalDateTime.now(), LocalDateTime.now(), false);

            // when
            userRepository.save(user);
            pointRepository.save(point);
            Point result = pointService.getUserPoint(user.getId());

            // then
            assertNotNull(result);
            assertEquals(1000L, result.getUserAmount());
            assertEquals(user.getId(), result.getUserId());
        }
    }
}