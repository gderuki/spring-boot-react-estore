package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
public record ProductDTO(
        String id,
        String name,
        boolean inStock,
        List<String> gallery,
        String description,
        String category,
        List<AttributeSetDTO> attributes,
        List<PriceDTO> prices
) {
}