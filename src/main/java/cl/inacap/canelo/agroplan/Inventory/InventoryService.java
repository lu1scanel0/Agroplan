package cl.inacap.canelo.agroplan.Inventory;

import cl.inacap.canelo.agroplan.Product.Product;
import cl.inacap.canelo.agroplan.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryMovementRepository movementRepository;

    // Aumentar stock y registrar movimiento
    public Product addStock(String productCode, int quantity) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        // Registrar movimiento
        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .movementDate(LocalDateTime.now())
                .quantityChange(quantity)
                .type("IN")
                .build();
        movementRepository.save(movement);

        return product;
    }

    // Disminuir stock y registrar movimiento
    public Product reduceStock(String productCode, int quantity) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("No hay suficiente stock para disminuir");
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        // Registrar movimiento
        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .movementDate(LocalDateTime.now())
                .quantityChange(-quantity)
                .type("OUT")
                .build();
        movementRepository.save(movement);

        return product;
    }

    // Obtener productos con stock crítico (coverageArea < 5)
    public List<Product> getCriticalStockProducts() {
        return productRepository.findAll().stream()
                .filter(p -> p.getCoverageArea() < 5)
                .toList();
    }

    // Sugerir adquisición de productos (coverageArea < 5)
    public List<Product> suggestAcquisition() {
        return getCriticalStockProducts();
    }

    // Informar ubicación de un producto
    public String getProductLocation(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return product.getUbication();
    }

    // Productos que caducan en 15 días
    public List<Product> getProductsExpiringIn15Days() {
        LocalDate thresholdDate = LocalDate.now().plusDays(15);
        return productRepository.findAll().stream()
                .filter(p -> !p.getCaducityDate().isAfter(thresholdDate))
                .toList();
    }



    // Obtener el costo total del inventario (sumatoria del stockQuantity * costProduct de todos los productos)
    public Integer getTotalInventoryCost() {
        return productRepository.findAll().stream()
                .mapToInt(p -> p.getStockQuantity() * p.getCostProduct())
                .sum();
    }

    // Obtener el costo de un producto específico (stockQuantity * costProduct)
    public Integer getProductCost(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return product.getStockQuantity() * product.getCostProduct();
    }

    // Obtener historial completo de movimientos
    public List<InventoryMovement> getAllMovements() {
        return movementRepository.findAll();
    }

    // Obtener movimientos de un producto específico
    public List<InventoryMovement> getMovementsByProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return movementRepository.findAll().stream()
                .filter(m -> m.getProduct().getId().equals(product.getId()))
                .toList();
    }
}
