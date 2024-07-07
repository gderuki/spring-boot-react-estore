package lv.psanatovs.api.repository.attribute;

import lv.psanatovs.api.entity.attribute.AttributeSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeSetRepository  extends JpaRepository<AttributeSet, Long> {
    List<AttributeSet> findByProductId(Long productId);
}
