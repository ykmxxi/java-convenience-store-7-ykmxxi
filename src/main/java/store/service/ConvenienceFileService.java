package store.service;

import java.util.List;

import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.PromotionPeriod;
import store.domain.Promotions;
import store.presentation.dto.ProductSaveRequest;
import store.presentation.dto.PromotionSaveRequest;

public class ConvenienceFileService {

    private final Products products = new Products();
    private final Promotions promotions = new Promotions();

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

    public List<Product> getProducts() {
        return products.getProducts();
    }

    public Promotion findPromotionByName(final String promotionName) {
        return promotions.findPromotionByName(promotionName);
    }

}
