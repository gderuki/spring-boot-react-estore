package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
@Getter
@Setter
public class AttributeSetDTO {
    public String name;
    public List<AttributeItemDTO> items;
}
