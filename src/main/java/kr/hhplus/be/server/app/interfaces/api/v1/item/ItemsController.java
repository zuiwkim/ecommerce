package kr.hhplus.be.server.app.interfaces.api.v1.item;

import kr.hhplus.be.server.app.application.item.GetItemsResult;
import kr.hhplus.be.server.app.application.item.ItemFacade;
import kr.hhplus.be.server.app.interfaces.api.common.CommonRes;
import kr.hhplus.be.server.app.interfaces.api.v1.item.res.GetItemsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/items")
public class ItemsController {

    private final ItemFacade itemFacade;

    @GetMapping
    public CommonRes<List<GetItemsRes>> getItemsResult(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<GetItemsResult> result = itemFacade.getItemResults(pageable);
        return CommonRes.success(GetItemsRes.of(result));
    }

    @GetMapping("/{itemId}")
    public CommonRes<GetItemsRes> getItemResult(@PathVariable Long itemId){
        GetItemsResult result = itemFacade.getItemResult(itemId);
        return CommonRes.success(new GetItemsRes(result.itemName(), result.itemPrice(), result.itemQuantity()));
    }
}
