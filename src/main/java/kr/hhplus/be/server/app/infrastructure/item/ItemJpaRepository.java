package kr.hhplus.be.server.app.infrastructure.item;

import kr.hhplus.be.server.app.domain.item.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Items, Long> {
}
