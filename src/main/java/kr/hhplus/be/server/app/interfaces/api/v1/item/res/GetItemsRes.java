package kr.hhplus.be.server.app.interfaces.api.v1.item.res;

import kr.hhplus.be.server.app.application.item.GetItemsResult;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public record GetItemsRes(
    String itemName,
    int itemPrice,
    int itemQuantity
) {

    public static List<GetItemsRes> of(Page<GetItemsResult> resultList) {
        List<GetItemsRes> resList = new ArrayList<>();

        for(GetItemsResult result : resultList.getContent()) {
            resList.add(new GetItemsRes(
                    result.itemName(),
                    result.itemPrice(),
                    result.itemQuantity()
            ));
        }

        return resList;
    }
}
