package kg.eventBish.repository;

import kg.eventBish.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.quantity < 5")
    public List<Product> findProductsWithQuantityLessThan5();
}
