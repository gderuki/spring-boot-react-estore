package lv.psanatovs.api.graphql.types;

import java.util.List;

public record AttributeInput (String name, List<AttributeItemInput> items) {
}
