package kg.eventBish.service;

import kg.eventBish.model.Product;
import kg.eventBish.repository.IProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepo;

    @Override
    public void createProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public List<Product> listProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product findProduct(Long productId) {
        return productRepo.findById(productId).orElse(null);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId);
    }

    @Override
    public Product editProduct(Long productId, Long newProductId, String newName, String newBrand, Double newCost, Double newQuantity) {
        Product product = productRepo.findById(productId).orElse(null);
        if (newProductId != null) {
            product.setId(newProductId);
        }
        if (newName != null) {
            product.setName(newName);
        }
        if (newBrand != null) {
            product.setBrand(newBrand);
        }
        if (newCost != null) {
            product.setCost(newCost);
        }
        if (newQuantity != null) {
            product.setQuantity(newQuantity);
        }
        productRepo.save(product);
        return product;
    }

    @Override
    public List<Product> productsWithLowStock() {
        return productRepo.findProductsWithQuantityLessThan5();
    }
}
