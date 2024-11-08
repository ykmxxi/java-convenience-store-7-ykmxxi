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

}
