package lv.psanatovs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"__typename"})
public record CurrencyDTO(
        String label,
        String symbol
) {
}