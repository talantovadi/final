package kg.eventBish.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesSummaryDayDTO {
    private LocalDate date;
    private int numberOfSales;
    private int numberOfProducts;
    private Double totalSalesAmount;
}
