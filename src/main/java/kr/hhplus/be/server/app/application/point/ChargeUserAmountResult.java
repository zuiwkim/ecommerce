package kr.hhplus.be.server.app.application.point;

public record ChargeUserAmountResult(
    Long userId,
    Long chargeAmount,
    Long amount){
}
