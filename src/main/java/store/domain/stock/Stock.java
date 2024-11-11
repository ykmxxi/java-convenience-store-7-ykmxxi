package store.domain.stock;

public class Stock {

    private static final int NONE = 0;
    private final int normal;
    private final int promotion;

    public Stock(final int normal, final int promotion) {
        validateStockQuantity(normal);
        validateStockQuantity(promotion);
        this.normal = normal;
        this.promotion = promotion;
    }

    private void validateStockQuantity(final int quantity) {
        if (quantity < NONE) {
            throw new IllegalStateException("재고 수량 정보가 잘못 됐습니다.");
        }
    }

    public boolean isEnough(final int quantity) {
        return (normal + promotion) >= quantity;
    }

    public boolean isEnoughPromotionStock(final int quantity) {
        return promotion >= quantity;
    }

    public boolean isOverPromotionStock(final int quantity) {
        return promotion < quantity;
    }

    public int normal() {
        return normal;
    }

    public int promotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return "Stock[" +
                "normal=" + normal +
                ", promotion=" + promotion +
                ']';
    }

}
