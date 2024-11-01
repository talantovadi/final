package kg.eventBish.service;

import kg.eventBish.dto.SalesSummaryDayDTO;
import kg.eventBish.dto.SaleDTO;
import kg.eventBish.dto.TopSaleDTO;
import kg.eventBish.model.Product;
import kg.eventBish.model.Sale;
import kg.eventBish.repository.IClientRepository;
import kg.eventBish.repository.IProductRepository;
import kg.eventBish.repository.ISaleRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService implements ISaleService {
    @Autowired
    private ISaleRepository saleRep;
    @Autowired
    private IProductRepository productRep;
    @Autowired
    private IClientRepository clientRep;

    @Override
    public void createSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setDate(saleDTO.getDate());
        sale.setClient(clientRep.findById(saleDTO.getClientId()).orElse(null));
        List<Product> products = new ArrayList<>();
        for (Long id : saleDTO.getProductIds()) {
            Product product = productRep.findById(id).orElse(null);
            if (product != null && product.getQuantity() < 1) {
                throw new RuntimeException("Cannot complete the sale. Product " + product.getName() + " is out of stock.");
            }
            if (product != null) {
                product.setQuantity(product.getQuantity() - 1);
                productRep.save(product);
                products.add(product);
            }
        }
        sale.setProducts(products);
        saleRep.save(sale);
    }

    @Override
    public List<Sale> listSales() {
        return saleRep.findAll();
    }

    @Override
    public Sale findSale(Long saleId) {
        return saleRep.findById(saleId).orElse(null);
    }

    @Override
    public void deleteSale(Long saleId) {
        adjustStockOnDelete(saleId);
        saleRep.deleteById(saleId);
    }

    @Override
    public Sale editSale(Long saleId, SaleDTO updatedSale) {
        Sale sale = saleRep.findById(saleId).orElse(null);
        if (sale != null) {
            if (updatedSale.getDate() != null && !updatedSale.getDate().equals(sale.getDate())) {
                sale.setDate(updatedSale.getDate());
            }
            if (updatedSale.getClientId() != null && !updatedSale.getClientId().equals(sale.getClient().getClientId())) {
                sale.setClient(clientRep.findById(updatedSale.getClientId()).orElse(null));
            }
            List<Product> newProducts = new ArrayList<>();
            for (Long id : updatedSale.getProductIds()) {
                Product product = productRep.findById(id).orElse(null);
                if (product != null) {
                    newProducts.add(product);
                }
            }
            List<Product> removedProducts = getRemovedProducts(sale.getProducts(), newProducts);
            List<Product> addedProducts = getAddedProducts(sale.getProducts(), newProducts);
            adjustStock(removedProducts, addedProducts);
            sale.setProducts(newProducts);
            saleRep.save(sale);
        }
        return sale;
    }

    private void adjustStockOnDelete(Long saleId) {
        Sale sale = saleRep.findById(saleId).orElse(null);
        if (sale != null) {
            for (Product product : sale.getProducts()) {
                product.setQuantity(product.getQuantity() + 1);
            }
            productRep.saveAll(sale.getProducts());
        }
    }

    private void adjustStock(List<Product> removedProducts, List<Product> addedProducts) {
        for (Product product : removedProducts) {
            product.setQuantity(product.getQuantity() + 1);
        }
        productRep.saveAll(removedProducts);
        for (Product product : addedProducts) {
            System.out.println("NAME: " + product.getName() + "| QUANTITY: " + product.getQuantity());
            if (product.getQuantity() < 1) {
                System.out.println("QUANTITY LESS THAN 1 THROWING EXCEPTION");
                throw new RuntimeException("Cannot complete the sale. Product " + product.getName() + " is out of stock.");
            }
            product.setQuantity(product.getQuantity() - 1);
        }
        productRep.saveAll(addedProducts);
    }

    private List<Product> getAddedProducts(List<Product> originalProducts, List<Product> newProducts) {
        Map<Product, Integer> originalProductCounts = getProductCountMap(originalProducts);
        Map<Product, Integer> newProductCounts = getProductCountMap(newProducts);

        List<Product> addedProducts = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : newProductCounts.entrySet()) {
            Product product = entry.getKey();
            Integer newCount = entry.getValue();
            Integer originalCount = originalProductCounts.getOrDefault(product, 0);
            if (newCount > originalCount) {
                int addedCount = newCount - originalCount;
                for (int i = 0; i < addedCount; i++) {
                    addedProducts.add(product);
                }
            }
        }

        return addedProducts;
    }

    private List<Product> getRemovedProducts(List<Product> originalProducts, List<Product> newProducts) {
        Map<Product, Integer> originalProductCounts = getProductCountMap(originalProducts);
        Map<Product, Integer> newProductCounts = getProductCountMap(newProducts);

        List<Product> removedProducts = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : originalProductCounts.entrySet()) {
            Product product = entry.getKey();
            Integer originalCount = entry.getValue();
            Integer newCount = newProductCounts.getOrDefault(product, 0);
            if (originalCount > newCount) {
                int removedCount = originalCount - newCount;
                for (int i = 0; i < removedCount; i++) {
                    removedProducts.add(product);
                }
            }
        }

        return removedProducts;
    }

    private Map<Product, Integer> getProductCountMap(List<Product> products) {
        Map<Product, Integer> productCountMap = new HashMap<>();
        for (Product product : products) {
            productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
        }
        return productCountMap;
    }

    @Override
    public SalesSummaryDayDTO dailySalesSummary(LocalDate date) {
        List<Sale> sales = saleRep.findSalesByDate(date);
        SalesSummaryDayDTO summary = new SalesSummaryDayDTO();
        summary.setDate(date);
        Double totalSales = 0.0;
        for (Sale sale : sales) {
            summary.setTotalSalesAmount((double) (summary.getNumberOfProducts() + sale.getProducts().size()));
            summary.setNumberOfSales(summary.getNumberOfSales() + 1);
            for (Product product : sale.getProducts()) {
                totalSales += product.getCost();
            }
            summary.setTotalSalesAmount(totalSales);
        }
        return summary;
    }

    @Override
    public TopSaleDTO highestSale() {
        Sale sale = saleRep.findSaleWithMaxTotal();
        TopSaleDTO highestSale = new TopSaleDTO();
        highestSale.setTotalAmount(sale.getProducts().size());
        highestSale.setSaleId(sale.getSaleId());
        highestSale.setDate(sale.getDate());
        highestSale.setClientFullName(sale.getClient().getFirstName() + " " + sale.getClient().getLastName());
        Double totalAmount = 0.0;
        for (Product product : sale.getProducts()) {
            totalAmount += product.getCost();
        }
        highestSale.setTotalAmount(totalAmount);
        return highestSale;
    }
}
