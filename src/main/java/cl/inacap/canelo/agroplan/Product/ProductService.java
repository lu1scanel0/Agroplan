package cl.inacap.canelo.agroplan.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Integer id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setProductCode(productDetails.getProductCode());
        product.setActiveSubstance(productDetails.getActiveSubstance());
        product.setBrandName(productDetails.getBrandName());
        product.setProductType(productDetails.getProductType());
        product.setProductFunction(productDetails.getProductFunction());
        product.setAuthorizedMarkets(productDetails.getAuthorizedMarkets());
        product.setAuthorizedGracePeriod(productDetails.getAuthorizedGracePeriod());
        product.setSpecificRestrictions(productDetails.getSpecificRestrictions());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setCoverageArea(productDetails.getCoverageArea());
        product.setCostProduct(productDetails.getCostProduct());
        product.setUnitMeasure(productDetails.getUnitMeasure());
        product.setReceptionDate(productDetails.getReceptionDate());
        product.setCaducityDate(productDetails.getCaducityDate());
        product.setUbication(productDetails.getUbication());

        return productRepository.save(product);
    }


    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productRepository.delete(product);
    }
}
