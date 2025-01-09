package kr.hhplus.be.server.app.interfaces.api.v1.point;

import kr.hhplus.be.server.app.application.point.GetUserAmountResult;
import kr.hhplus.be.server.app.application.point.PointFacade;
import kr.hhplus.be.server.app.interfaces.api.common.CommonRes;
import kr.hhplus.be.server.app.interfaces.api.v1.point.req.ChargeUserAmountReq;
import kr.hhplus.be.server.app.interfaces.api.v1.point.res.ChargeUserAmountRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/points")
public class PointController {

    private final PointFacade pointFacade;

    @GetMapping("/{userId}/amount")
    public CommonRes<GetUserAmountResult> getUserAmount(@PathVariable Long userId){
        return CommonRes.success(pointFacade.getUserPoint(userId));
    }

    @PostMapping("/{userId}/charge")
    public CommonRes<ChargeUserAmountRes> chargeUserAmount(@PathVariable Long userId, @RequestBody ChargeUserAmountReq req){
        ChargeUserAmountResult result = pointFacade.chargeUserPoint(userId, req.chargePoint());
        return CommonRes.success(new ChargeUserAmountRes(result.userId(), result.chargePoint(), result.amount()));
    }

}
