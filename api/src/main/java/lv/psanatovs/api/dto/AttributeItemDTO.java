package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
@Getter
@Setter
@NoArgsConstructor
public class AttributeItemDTO {
    public String value;
    public String displayValue;

    public AttributeItemDTO(String value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }
}