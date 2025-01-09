package kr.hhplus.be.server.app.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

//    @Transactional(readOnly = true)
//    public Item getItem(Long itemId) {
//        return itemRepository.findByitemId(itemId);
//    }
//
//    @Transactional
//    public Point chargeUserPoint(Long id, Long chargePoint) {
//        return point;
//    }

    @Transactional(readOnly = true)
    public Items getItemResult(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Page<Items> getItemResults(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
}