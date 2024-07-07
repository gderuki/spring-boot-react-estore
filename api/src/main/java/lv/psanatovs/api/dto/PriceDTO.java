package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
@Getter
@Setter
public class PriceDTO {
    public double amount;
    public CurrencyDTO currency;
}