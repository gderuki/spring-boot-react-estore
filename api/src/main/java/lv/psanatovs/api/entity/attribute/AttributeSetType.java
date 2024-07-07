package lv.psanatovs.api.entity.attribute;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table(name = "attribute_set_types")
@Entity
@NoArgsConstructor
public class AttributeSetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public AttributeSetType(String name) {
        this.name = name;
    }
}
