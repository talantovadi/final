package kg.eventBish.controller;

import kg.eventBish.dto.SalesSummaryDayDTO;
import kg.eventBish.dto.SaleDTO;
import kg.eventBish.dto.TopSaleDTO;
import kg.eventBish.model.Product;
import kg.eventBish.model.Sale;
import kg.eventBish.service.ISaleService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {
    @Autowired
    private ISaleService saleService;

    @PostMapping("/sales/create")
    public String createSale(@RequestBody SaleDTO saleDto){
        saleService.createSale(saleDto);
        return "Sale created successfully.";
    }

    @GetMapping("/sales")
    public List<Sale> listSales(){
        return saleService.listSales();
    }

    @GetMapping("/sales/{saleId}")
    public Sale getSale(@PathVariable Long saleId){
        return saleService.findSale(saleId);
    }

    @DeleteMapping("/sales/delete/{saleId}")
    public String deleteSale(@PathVariable Long saleId){
        saleService.deleteSale(saleId);
        return "Sale deleted successfully.";
    }

    @PutMapping("/sales/edit/{saleId}")
    public Sale editSale(@PathVariable Long saleId, @RequestBody SaleDTO newSale){
        return saleService.editSale(saleId, newSale);
    }

    @GetMapping("/sales/products/{saleId}")
    public List<Product> getSaleProducts(@PathVariable Long saleId){
        Sale sale = saleService.findSale(saleId);
        return sale.getProducts();
    }

    @GetMapping("/sales/summary/{date}")
    public SalesSummaryDayDTO dailySummary(@PathVariable LocalDate date){
        return saleService.dailySalesSummary(date);
    }

    @GetMapping("/sales/top_sale")
    public TopSaleDTO topSale(){
        return saleService.highestSale();
    }
}
