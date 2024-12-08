package cl.inacap.canelo.agroplan.Inventory;

import cl.inacap.canelo.agroplan.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    // Aumentar stock
    @PostMapping("/addStock")
    @PreAuthorize("hasRole('ADMIN')")
    public Product addStock(@RequestParam String productCode, @RequestParam int quantity) {
        return inventoryService.addStock(productCode, quantity);
    }

    // Disminuir stock
    @PostMapping("/reduceStock")
    @PreAuthorize("hasRole('ADMIN')")
    public Product reduceStock(@RequestParam String productCode, @RequestParam int quantity) {
        return inventoryService.reduceStock(productCode, quantity);
    }

    // Productos con stock crítico
    @GetMapping("/criticalStock")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Product> getCriticalStockProducts() {
        return inventoryService.getCriticalStockProducts();
    }

    // Sugerir adquisición
    @GetMapping("/suggestAcquisition")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Product> suggestAcquisition() {
        return inventoryService.suggestAcquisition();
    }

    // Ubicación de un producto
    @GetMapping("/location")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String getProductLocation(@RequestParam String productCode) {
        return inventoryService.getProductLocation(productCode);
    }

    // Productos que caducan en 15 días
    @GetMapping("/expiringProducts")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<Product> getProductsExpiringIn15Days() {
        return inventoryService.getProductsExpiringIn15Days();
    }

    // Reporte de costo total del inventario
    @GetMapping("/totalCost")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Integer getTotalCost() {
        return inventoryService.getTotalInventoryCost();
    }

    // Reporte de costo por producto
    @GetMapping("/productCost")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Integer getProductCost(@RequestParam String productCode) {
        return inventoryService.getProductCost(productCode);
    }

    // Historial completo de movimientos
    @GetMapping("/movements")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<InventoryMovement> getAllMovements() {
        return inventoryService.getAllMovements();
    }

    // Historial de movimientos de un producto específico
    @GetMapping("/movementsByProduct")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<InventoryMovement> getMovementsByProduct(@RequestParam String productCode) {
        return inventoryService.getMovementsByProduct(productCode);
    }
}
