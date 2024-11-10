package store.domain.inventory;

import java.util.Objects;

public class PromotionProduct {

    private final Product product;
    private final Promotion promotion;

    public PromotionProduct(final Product product, final Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public boolean isShortageOrder(final int promotionCount, final int orderQuantity) {
        return promotion.isShortage(promotionCount, orderQuantity);
    }

    public boolean isOverOrder(final int promotionCount, final int orderQuantity) {
        return promotion.isOver(promotionCount, orderQuantity);
    }

    public int calculatePromotionProductCount(final int promotionCount) {
        return promotion.promotionType().calculateQuantityForGetFree() * promotionCount;
    }

    public Promotion promotion() {
        return promotion;
    }

    public Product product() {
        return product;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromotionProduct that)) {
            return false;
        }
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }

}
