package store.domain.promotion;

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

    public boolean isPromotionPeriod(final LocalDateTime localDateTime) {
        return promotionPeriod.isBetween(localDateTime);
    }

    public int calculatePromotionCount(final int stockQuantity, final int orderQuantity) {
        int quantityForGetFree = minimumQuantity();
        int maxPromotionCount = stockQuantity / quantityForGetFree;
        if (orderQuantity <= quantityOfMaxPromotionCount(maxPromotionCount, quantityForGetFree)) {
            return orderQuantity / quantityForGetFree;
        }
        return maxPromotionCount;
    }

    private int quantityOfMaxPromotionCount(final int maxPromotionCount, final int quantityForGetFree) {
        return maxPromotionCount * quantityForGetFree;
    }

    public boolean isNeedOneMore(final int orderQuantity) {
        return orderQuantity == (minimumQuantity() - 1);
    }

    public boolean isOrderQuantityShortage(final int orderQuantity) {
        return promotionType.isOrderQuantityShortage(orderQuantity);
    }

    public int minimumQuantity() {
        return this.promotionType.calculateQuantityForGetFree();
    }

    public PromotionType promotionType() {
        return promotionType;
    }

    public String name() {
        return promotionType.getName();
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotionType=" + promotionType +
                ", promotionPeriod=" + promotionPeriod +
                '}';
    }

}
