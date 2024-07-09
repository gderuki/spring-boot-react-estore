package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
public record AttributeSetDTO(
        Long id,
        String name,
        List<AttributeItemDTO> items
) {
}
