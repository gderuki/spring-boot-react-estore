package lv.psanatovs.api.repository.attribute;

import lv.psanatovs.api.entity.attribute.AttributeSetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeSetTypeRepository extends JpaRepository<AttributeSetType, Long> {
    Optional<AttributeSetType> findByName(String name);
}
