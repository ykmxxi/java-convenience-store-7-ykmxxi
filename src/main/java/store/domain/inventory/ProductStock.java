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

    public boolean isEnough(final int quantity) {
        return stock.isEnough(quantity);
    }

    public boolean hasEnoughPromotionStock(final int quantity) {
        return stock.isEnoughPromotionStock(quantity);
    }

    public int promotionQuantity() {
        return stock.promotion();
    }

    public int normalQuantity() {
        return stock.normal();
    }

}
