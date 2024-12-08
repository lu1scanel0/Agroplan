package cl.inacap.canelo.agroplan.Product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chemicalProduct", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productCode"})
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String productCode;
    @Column(nullable = false)
    private String activeSubstance;
    @Column(nullable = false)
    private String brandName;
    @Column(nullable = false)
    private String productType;
    @Column(nullable = false)
    private String productFunction;
    @Column(nullable = false)
    private String authorizedMarkets;
    @Column(nullable = false)
    private Integer authorizedGracePeriod;
    @Column(nullable = false)
    private String specificRestrictions;
    @Column(nullable = false)
    private Integer stockQuantity;
    @Column(nullable = false)
    private Double coverageArea;
    @Column(nullable = false)
    private Integer costProduct;
    @Column(nullable = false)
    private String unitMeasure;
    @Column(nullable = false)
    private LocalDate receptionDate;
    @Column(nullable = false)
    private LocalDate caducityDate;
    @Column(nullable = false)
    private String ubication;
}
