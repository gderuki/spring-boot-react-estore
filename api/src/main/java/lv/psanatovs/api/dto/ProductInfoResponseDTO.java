package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ProductInfoResponseDTO(
        String id,
        String name,
        boolean inStock,
        String description,
        String category,
        List<AttributeSetDTO> attributes,
        List<PriceDTO> prices,
        List<String> gallery
) {
}
