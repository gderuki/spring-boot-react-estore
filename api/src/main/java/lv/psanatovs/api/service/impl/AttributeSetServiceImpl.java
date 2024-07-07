package lv.psanatovs.api.service.impl;

import lv.psanatovs.api.entity.attribute.AttributeItem;
import lv.psanatovs.api.repository.attribute.AttributeItemRepository;
import lv.psanatovs.api.service.AttributeSetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeSetServiceImpl implements AttributeSetService {
    private final AttributeItemRepository attributeItemRepository;

    public AttributeSetServiceImpl(AttributeItemRepository attributeItemRepository) {
        this.attributeItemRepository = attributeItemRepository;
    }

    @Override
    public List<AttributeItem> findItemsByAttributeSet(Long attributeSetId) {
        return attributeItemRepository.findByAttributeSetId(attributeSetId);
    }
}
