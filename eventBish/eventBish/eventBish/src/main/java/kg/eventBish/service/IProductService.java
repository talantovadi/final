package kg.eventBish.service;

import kg.eventBish.model.Product;
import java.util.List;

public interface IProductService {
    // Create product
    public void createProduct(Product product);
    // List of products
    public List<Product> listProducts();
    // Get particular product
    public Product findProduct(Long productId);
    // Delete product
    public void deleteProduct(Long productId);
    // Edit product
    public Product editProduct(Long productId, Long newProductId, String newName, String newBrand, Double newCost, Double newQuantity);
    // Get products with low stock
    public List<Product> productsWithLowStock();
}
