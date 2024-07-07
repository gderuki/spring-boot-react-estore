package lv.psanatovs.api.entity.attribute;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "attribute_items")
@NoArgsConstructor
public class AttributeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String displayValue;

    @ManyToOne
    @JoinColumn(name = "attribute_set_id")
    private AttributeSet attributeSet;
}