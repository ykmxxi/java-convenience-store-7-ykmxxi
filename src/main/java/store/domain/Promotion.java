package store.domain;

import java.util.Objects;

public class Promotion {

    private final String promotionName;
    private final PromotionType promotionType;
    private final PromotionPeriod promotionPeriod;

    public Promotion(final String promotionName, final PromotionPeriod promotionPeriod) {
        this.promotionName = promotionName;
        this.promotionType = PromotionType.findByName(promotionName);
        this.promotionPeriod = promotionPeriod;
    }

    public boolean isSameName(final String promotionName) {
        return this.promotionName.equals(promotionName);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion promotion)) {
            return false;
        }
        return Objects.equals(promotionName, promotion.promotionName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(promotionName);
    }

}
