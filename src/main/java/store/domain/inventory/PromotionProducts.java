package store.domain.inventory;

import java.util.Set;

public class PromotionProducts {

    private final Promotion promotion;
    private final Set<Product> products;

    public PromotionProducts(final Promotion promotion, final Set<Product> products) {
        this.promotion = promotion;
        this.products = products;
    }

    public Promotion promotion() {
        return promotion;
    }

}
