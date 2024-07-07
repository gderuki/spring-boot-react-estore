package lv.psanatovs.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.psanatovs.api.util.StringUtils;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 2500)
    private String description;
    private String category;
    private boolean inStock;

    @Column(unique = true)
    private String kebabCaseName;

    @PrePersist
    @PreUpdate
    private void updateKebabCaseName() {
        this.kebabCaseName = StringUtils.toKebabCase(name);
    }
}