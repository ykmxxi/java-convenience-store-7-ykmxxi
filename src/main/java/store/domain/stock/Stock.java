package store.domain.stock;

public class Stock {

    private static final int NONE = 0;
    private final int normal;
    private final int promotion;

    public Stock(final int normal, final int promotion) {
        this.normal = normal;
        this.promotion = promotion;
    }

    public static Stock decrease(final Stock stock, final int quantity) {
        if (stock.promotion >= quantity) {
            return new Stock(stock.normal, stock.promotion - quantity);
        }
        int remain = quantity - stock.promotion;
        return new Stock(stock.normal - remain, NONE);
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
