package lv.psanatovs.api.entity.attribute;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.psanatovs.api.entity.Product;

@Setter
@Getter
@Table(name = "attribute_sets")
@Entity
@NoArgsConstructor
public class AttributeSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AttributeSetType type;
}