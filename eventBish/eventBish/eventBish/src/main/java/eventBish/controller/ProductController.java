package eventBish.controller;

import eventBish.model.Product;
import eventBish.service.IProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private IProductService productService;

    @PostMapping("/products/create")
    public String createProduct(@RequestBody Product product){
        productService.createProduct(product);
        return "Product registered successfully";
    }

    @GetMapping("/products")
    public List<Product> listProducts(){
        return productService.listProducts();
    }

    @GetMapping("/products/low_stock")
    public List<Product> listLowStock(){
        return productService.productsWithLowStock();
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable Long productId){
        return productService.findProduct(productId);
    }

    @DeleteMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "Product deleted successfully";
    }

    @PutMapping("/products/edit/{productId}")
    public Product editProduct(@PathVariable Long productId,
                               @RequestParam(required = false, name="newProductId") Long newProductId,
                               @RequestParam(required = false, name="newName") String newName,
                               @RequestParam(required = false, name="newBrand") String newBrand,
                               @RequestParam(required = false, name="newCost") Double newCost,
                               @RequestParam(required = false, name="newQuantity") Double newQuantity){
        return productService.editProduct(productId, newProductId, newName, newBrand, newCost, newQuantity);
    }
}
