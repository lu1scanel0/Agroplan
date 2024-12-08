package cl.inacap.canelo.agroplan.Inventory;

import cl.inacap.canelo.agroplan.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_movement")
public class InventoryMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private LocalDateTime movementDate;

    @Column(nullable = false)
    private int quantityChange; // positivo si es entrada, negativo si es salida

    @Column(nullable = false)
    private String type; // "IN" o "OUT"
}
