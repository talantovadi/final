package kg.eventBish.repository;

import kg.eventBish.model.Sale;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT v FROM Sale v WHERE v.date = ?1")
    public List<Sale> findSalesByDate(LocalDate date);

    @Query("SELECT v " +
            "FROM Sale v " +
            "WHERE (SELECT SUM(pr.cost) FROM v.products pr) = " +
            "(SELECT MAX(totalCost) FROM (SELECT SUM(pr2.cost) AS totalCost FROM Sale v2 JOIN v2.products pr2 GROUP BY v2))")
    public Sale findSaleWithMaxTotal();
}
