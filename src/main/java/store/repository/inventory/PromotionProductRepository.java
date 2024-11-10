package store.repository.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.inventory.Promotion;
import store.domain.inventory.PromotionProducts;
import store.repository.Repository;

public class PromotionProductRepository implements Repository<PromotionProducts, Promotion> {

    private static final Map<Promotion, PromotionProducts> PROMOTION_PRODUCTS_INVENTORY = new HashMap<>();

    @Override
    public PromotionProducts find(final Promotion promotion) {
        return PROMOTION_PRODUCTS_INVENTORY.get(promotion);
    }

    @Override
    public List<PromotionProducts> findAll() {
        return PROMOTION_PRODUCTS_INVENTORY.values()
                .stream()
                .toList();
    }

    @Override
    public List<PromotionProducts> saveAll(final Iterable<PromotionProducts> entities) {
        for (PromotionProducts promotionProducts : entities) {
            PROMOTION_PRODUCTS_INVENTORY.put(promotionProducts.promotion(), promotionProducts);
        }
        return List.of();
    }

}
