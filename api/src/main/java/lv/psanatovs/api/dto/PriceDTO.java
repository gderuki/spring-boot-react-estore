package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
public record PriceDTO(
        double amount,
        CurrencyDTO currency
) {
}