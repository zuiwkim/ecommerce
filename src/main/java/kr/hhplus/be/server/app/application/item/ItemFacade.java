package kr.hhplus.be.server.app.application.item;

import kr.hhplus.be.server.app.domain.item.Items;
import kr.hhplus.be.server.app.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemFacade {

    private final ItemService itemService;

    public Page<GetItemsResult> getItemResults(Pageable pageable) {
        Page<Items> itemsPage = itemService.getItemResults(pageable);
        return itemsPage.map(items ->
                new GetItemsResult(
                        items.getItemName(),
                        items.getItemPrice(),
                        items.getItemQuantity()
                )
        );
    }

    public GetItemsResult getItemResult(Long itemId) {
        Items items = itemService.getItemResult(itemId);
        return new GetItemsResult(items.getItemName(), items.getItemPrice(), items.getItemQuantity());
    }

}