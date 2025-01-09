package kr.hhplus.be.server.app.domain.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class PointServiceUnitTest {

    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;

    @Nested
    class PointSuccessTests{
        @Test
        @DisplayName("[성공케이스] 유저의 포인트가 1000원이 있을경우, 500원 충전시 1500원의 잔액이 남는다")
        void userChargeAmountSuccessTest() {
            // given
            long pointId = 1L;
            long userId = 1L;
            long userAmount = 1000L;
            long chargeAmount = 500L;

            Point point = new Point(pointId, userId, userAmount, LocalDateTime.now(), LocalDateTime.now(), false);

            // when
            when(pointRepository.findByUserId(userId)).thenReturn(point);

            // then
            Point result = pointService.chargeUserPoint(userId, chargeAmount);
            assertNotNull(result);
            assertEquals(result.getUserAmount(),userAmount + chargeAmount);
        }

    }

    @Nested
    class PointFailTests{
        @Test
        @DisplayName("[실패케이스] 유저의 포인트를 9원 충전시 충전 최소금액 미만 에러 표출이 되야한다.")
        void userChargeAmountMinPointTest(){
            // given
            long userId = 1L;
            long chargeAmount = 9L;

            // when
            Exception exception = assertThrows(Exception.class, () -> pointService.chargeUserPoint(userId, chargeAmount));

            // then
            assertEquals("최소 충전금액 10원 미만입니다!", exception.getMessage());
            verify(pointRepository, never()).findByUserId(anyLong());
        }
    }

}