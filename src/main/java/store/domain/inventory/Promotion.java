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

    public boolean isPromotionPeriod(final LocalDateTime localDateTime) {
        return promotionPeriod.isBetween(localDateTime);
    }

    public int calculatePromotionCount(final int stockQuantity, final int orderQuantity) {
        int quantityForGetFree = promotionType.calculateQuantityForGetFree();
        int maxPromotionCount = stockQuantity / quantityForGetFree;
        if (orderQuantity < (maxPromotionCount * quantityForGetFree)) {
            return orderQuantity / quantityForGetFree;
        }
        return maxPromotionCount;
    }

    public boolean isShortage(final int promotionCount, final int quantity) {
        int quantityForGetFree = promotionType.calculateQuantityForGetFree();
        return quantityForGetFree * promotionCount > quantity;
    }

    public boolean isOver(final int promotionCount, final int orderQuantity) {
        int quantityForGetFree = promotionType.calculateQuantityForGetFree();
        return quantityForGetFree * promotionCount < orderQuantity;
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
