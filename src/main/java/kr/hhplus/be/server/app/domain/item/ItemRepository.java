package kr.hhplus.be.server.app.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepository {
    Items findById(Long itemId);
    Page<Items> findAll(Pageable pageable);
}
