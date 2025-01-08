package kr.hhplus.be.server.app.application.point;

public record GetUserAmountResult(Long userId,
                                  String userMail,
                                  Long amount) {

}
