package lv.psanatovs.api.repository.attribute;

import lv.psanatovs.api.entity.attribute.AttributeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeItemRepository extends JpaRepository<AttributeItem, Long> {
    List<AttributeItem> findByAttributeSetId(Long attributeSetId);
}
