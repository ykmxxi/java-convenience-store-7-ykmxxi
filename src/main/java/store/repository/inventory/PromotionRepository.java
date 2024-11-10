package store.repository.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.inventory.Promotion;
import store.domain.inventory.PromotionType;
import store.repository.Repository;

public class PromotionRepository implements Repository<Promotion, PromotionType> {

    private static final Map<PromotionType, Promotion> PROMOTION_INVENTORY = new HashMap<>();

    @Override
    public boolean exists(final PromotionType promotionType) {
        return PROMOTION_INVENTORY.containsKey(promotionType);
    }

    @Override
    public Promotion find(final PromotionType promotionType) {
        if (PROMOTION_INVENTORY.containsKey(promotionType)) {
            return PROMOTION_INVENTORY.get(promotionType);
        }
        throw new IllegalStateException("존재하지 않는 행사입니다.");
    }

    @Override
    public List<Promotion> findAll() {
        return PROMOTION_INVENTORY.values()
                .stream()
                .toList();
    }

    @Override
    public List<Promotion> saveAll(final Iterable<Promotion> entities) {
        List<Promotion> promotions = new ArrayList<>();
        for (Promotion promotion : entities) {
            PROMOTION_INVENTORY.put(promotion.promotionType(), promotion);
            promotions.add(promotion);
        }
        return promotions;
    }

}
