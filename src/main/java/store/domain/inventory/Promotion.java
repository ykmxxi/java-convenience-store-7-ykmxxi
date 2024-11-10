package store.domain.inventory;

import java.time.LocalDateTime;

public class Promotion {

    private final PromotionType promotionType;
    private final PromotionPeriod promotionPeriod;

    private Promotion(final PromotionType promotionType, final PromotionPeriod promotionPeriod) {
        this.promotionType = promotionType;
        this.promotionPeriod = promotionPeriod;
    }

    public static Promotion of(String name, int quantityOfBuy, int quantityOfFree,
                               LocalDateTime startDate, LocalDateTime endDate
    ) {
        PromotionType promotionType = PromotionType.from(name);
        promotionType.validateBuyNGetOneFree(quantityOfBuy, quantityOfFree);
        promotionType.validatePeriod(startDate, endDate);
        return new Promotion(promotionType, new PromotionPeriod(startDate, endDate));
    }

    public PromotionType promotionType() {
        return promotionType;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotionType=" + promotionType +
                ", promotionPeriod=" + promotionPeriod +
                '}';
    }

}
