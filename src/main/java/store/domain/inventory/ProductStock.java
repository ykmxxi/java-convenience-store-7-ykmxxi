package store.domain.inventory;

public class ProductStock {

    private final Product product;
    private Stock stock;

    public ProductStock(final Product product, final Stock stock) {
        this.product = product;
        this.stock = stock;
    }

    public Product product() {
        return product;
    }

    public boolean hasPromotionStock() {
        return stock.hasPromotion();
    }

    public boolean hasNormalStock() {
        return stock.hasNormal();
    }

    public int promotionQuantity() {
        return stock.promotion();
    }

    public int normalQuantity() {
        return stock.normal();
    }

}
