package lv.psanatovs.api.repository.price;

import lv.psanatovs.api.entity.price.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByProductId(Long productId);
}
