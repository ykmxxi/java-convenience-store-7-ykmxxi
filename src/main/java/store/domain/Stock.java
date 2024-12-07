package store.domain;

public class Stock {

    private final int normalQuantity;
    private final int promotionQuantity;

    public Stock(final int normalQuantity, final int promotionQuantity) {
        this.normalQuantity = normalQuantity;
        this.promotionQuantity = promotionQuantity;
    }

    public int normalQuantity() {
        return normalQuantity;
    }

    public int promotionQuantity() {
        return promotionQuantity;
    }

}
