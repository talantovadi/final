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
public class TopSaleDTO {
    private Long saleId;
    private double totalAmount;
    private int productQuantity;
    private LocalDate date;
    private String clientFullName;
}
