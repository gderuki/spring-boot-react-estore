package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductInfoResponseDTO {
    private String id;
    private String name;
    private boolean inStock;
    private String description;
    private String category;
    private List<AttributeSetDTO> attributes;
    private List<PriceDTO> prices;
    private List<String> gallery;
}
