package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private final List<Promotion> promotions = new ArrayList<>();

    public void save(final Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion findPromotionByName(final String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.isSameName(promotionName)) {
                return promotion;
            }
        }
        throw new IllegalArgumentException("%s은(는) 존재하지 않는 프로모션입니다.".formatted(promotionName));
    }

}
