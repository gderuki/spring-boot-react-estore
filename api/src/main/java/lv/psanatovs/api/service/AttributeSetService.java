package lv.psanatovs.api.service;

import lv.psanatovs.api.entity.attribute.AttributeItem;

import java.util.List;

public interface AttributeSetService {
    List<AttributeItem> findItemsByAttributeSet(Long attributeSetId);
}
