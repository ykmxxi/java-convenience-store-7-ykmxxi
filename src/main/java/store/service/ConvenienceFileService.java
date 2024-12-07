package store.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.Product;
import store.domain.ProductStock;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.PromotionPeriod;
import store.domain.Promotions;
import store.domain.Stock;
import store.presentation.dto.ProductSaveRequest;
import store.presentation.dto.PromotionSaveRequest;

public class ConvenienceFileService {

    private final Products products = new Products();
    private final Promotions promotions = new Promotions();
    private final ProductStock productStock = new ProductStock();

    public void saveProducts(final List<ProductSaveRequest> productSaveRequests) {
        for (ProductSaveRequest productSaveRequest : productSaveRequests) {
            Product product = new Product(productSaveRequest.name(), productSaveRequest.price());
            products.save(product);
        }
    }

    public void savePromotions(final List<PromotionSaveRequest> promotionSaveRequests) {
        for (PromotionSaveRequest promotionSaveRequest : promotionSaveRequests) {
            Promotion promotion = new Promotion(promotionSaveRequest.name(),
                    PromotionPeriod.of(promotionSaveRequest.startDate(), promotionSaveRequest.endDate()));
            promotions.save(promotion);
        }
    }

    public void saveProductsStock(final List<ProductSaveRequest> productSaveRequests) {
        Map<Product, Map<String, Integer>> stockQuantities = getStockQuantities(productSaveRequests);
        for (Product product : stockQuantities.keySet()) {
            saveStockQuantity(product, stockQuantities);
        }
    }

    private void saveStockQuantity(final Product product, final Map<Product, Map<String, Integer>> stockQuantities) {
        Map<String, Integer> quantities = stockQuantities.get(product);
        int normalQuantity = 0;
        int promotionQuantity = 0;
        if (quantities.containsKey("null")) {
            normalQuantity = quantities.get("null");
        }
        if (quantities.containsKey("promotion")) {
            promotionQuantity = quantities.get("promotion");
        }
        productStock.saveStock(product, new Stock(normalQuantity, promotionQuantity));
    }

    private Map<Product, Map<String, Integer>> getStockQuantities(final List<ProductSaveRequest> productSaveRequests) {
        Map<Product, Map<String, Integer>> stockQuantities = new HashMap<>();
        for (ProductSaveRequest productSaveRequest : productSaveRequests) {
            Map<String, Integer> quantities = stockQuantities.computeIfAbsent(
                    products.findByName(productSaveRequest.name()), (key) -> new HashMap<>());
            saveQuantity(productSaveRequest, quantities);
        }
        return stockQuantities;
    }

    private void saveQuantity(final ProductSaveRequest productSaveRequest, final Map<String, Integer> quantities) {
        if (productSaveRequest.promotion().equals("null")) {
            quantities.put("null", productSaveRequest.quantity());
            return;
        }
        quantities.put("promotion", productSaveRequest.quantity());
    }

    public List<Product> getProducts() {
        return products.getProducts();
    }

    public Promotion findPromotionByName(final String promotionName) {
        return promotions.findPromotionByName(promotionName);
    }

    public Stock findProductStock(final String productName) {
        return productStock.findStock(productName);
    }

}
