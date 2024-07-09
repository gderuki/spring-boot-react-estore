package lv.psanatovs.api.graphql.types;

import java.util.List;

public record ProductInput(String productId, Integer quantity, List<AttributeInput> attributes) {}
