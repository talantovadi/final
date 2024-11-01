package kg.eventBish.service;

import kg.eventBish.dto.SalesSummaryDayDTO;
import kg.eventBish.dto.SaleDTO;
import kg.eventBish.dto.TopSaleDTO;
import kg.eventBish.dto.SalesSummaryDayDTO;
import kg.eventBish.model.Sale;
import java.time.LocalDate;
import java.util.List;

public interface ISaleService {
    public void createSale(SaleDTO saleDTO);
    public List<Sale> listSales();
    public Sale findSale(Long saleId);
    public void deleteSale(Long saleId);
    public Sale editSale(Long saleId, SaleDTO newSale);
    // Summary of sales by date
    public SalesSummaryDayDTO dailySalesSummary(LocalDate date);
    // Sale with the highest amount
    public TopSaleDTO highestSale();

}
