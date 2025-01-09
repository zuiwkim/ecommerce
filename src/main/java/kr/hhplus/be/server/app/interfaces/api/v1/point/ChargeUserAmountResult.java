package kr.hhplus.be.server.app.interfaces.api.v1.point;

public record ChargeUserAmountResult(
    Long userId,
    Long chargePoint,
    Long amount)
{
}
