package store.domain.inventory;

import java.util.Set;

public class PromotionProduct {

    private final Promotion promotion;
    private final Product product;

    public PromotionProduct(final Promotion promotion, final Product product) {
        this.promotion = promotion;
        this.product = product;
    }

    public Promotion promotion() {
        return promotion;
    }

    public Product product() {
        return product;
    }

}
