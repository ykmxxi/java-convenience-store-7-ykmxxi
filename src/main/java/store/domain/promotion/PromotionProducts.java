package store.domain.promotion;

import java.util.HashMap;
import java.util.Map;

import store.domain.product.Product;

public class PromotionProducts {

    private static final Map<Product, PromotionProduct> PROMOTION_PRODUCTS = new HashMap<>();

    public void saveAll(final Iterable<PromotionProduct> promotionProducts) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            PROMOTION_PRODUCTS.put(promotionProduct.product(), promotionProduct);
        }
    }

    public boolean isPromotionProduct(final Product product) {
        return PROMOTION_PRODUCTS.containsKey(product);
    }

    public PromotionProduct find(final Product product) {
        return PROMOTION_PRODUCTS.get(product);
    }

    public boolean exists(final Product product) {
        return PROMOTION_PRODUCTS.containsKey(product);
    }

}
