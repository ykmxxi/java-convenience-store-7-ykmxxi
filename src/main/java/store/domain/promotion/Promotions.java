package store.domain.promotion;

import java.util.EnumMap;
import java.util.Map;

public class Promotions {

    private static final Map<PromotionType, Promotion> PROMOTIONS = new EnumMap<>(PromotionType.class);

    public void saveAll(final Iterable<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            PROMOTIONS.put(promotion.promotionType(), promotion);
        }
    }

    public Promotion find(final PromotionType promotionType) {
        return PROMOTIONS.get(promotionType);
    }

}
