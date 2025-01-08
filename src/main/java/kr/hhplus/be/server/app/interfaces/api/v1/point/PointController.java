package kr.hhplus.be.server.app.interfaces.api.v1.point;

import kr.hhplus.be.server.app.application.point.GetUserAmountResult;
import kr.hhplus.be.server.app.application.point.PointFacade;
import kr.hhplus.be.server.app.interfaces.api.common.CommonRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/points")
public class PointController {

    private final PointFacade pointFacade;

    @GetMapping("/{userId}/amount")
    public CommonRes<GetUserAmountResult> getUserAmount(@PathVariable Long userId){
        return CommonRes.success(pointFacade.getUserPoint(userId));
    }

}
