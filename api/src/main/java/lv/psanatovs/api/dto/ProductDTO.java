package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
@Getter
@Setter
public class ProductDTO {
    public String id;
    public String name;
    public boolean inStock;
    public List<String> gallery;
    public String description;
    public String category;
    public List<AttributeSetDTO> attributes;
    public List<PriceDTO> prices;
}