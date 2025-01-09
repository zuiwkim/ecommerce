package kr.hhplus.be.server.app.infrastructure.item;

import kr.hhplus.be.server.app.domain.item.Items;
import kr.hhplus.be.server.app.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemJpaRepository jpaRepository;

    @Override
    public Items findById(Long itemId) {
        return jpaRepository.findById(itemId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 아이템 입니다."));
    }

    @Override
    public Page<Items> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }
}
