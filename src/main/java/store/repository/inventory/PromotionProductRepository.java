package store.repository.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.inventory.Product;
import store.domain.inventory.PromotionProduct;
import store.repository.Repository;

public class PromotionProductRepository implements Repository<PromotionProduct, Product> {

    private static final Map<Product, PromotionProduct> PROMOTION_PRODUCTS_INVENTORY = new HashMap<>();

    @Override
    public boolean exists(final Product product) {
        return PROMOTION_PRODUCTS_INVENTORY.containsKey(product);
    }

    @Override
    public PromotionProduct find(final Product product) {
        return PROMOTION_PRODUCTS_INVENTORY.get(product);
    }

    @Override
    public List<PromotionProduct> findAll() {
        return PROMOTION_PRODUCTS_INVENTORY.values()
                .stream()
                .toList();
    }

    @Override
    public List<PromotionProduct> saveAll(final Iterable<PromotionProduct> entities) {
        for (PromotionProduct promotionProduct : entities) {
            PROMOTION_PRODUCTS_INVENTORY.put(promotionProduct.product(), promotionProduct);
        }
        return List.of();
    }

}
