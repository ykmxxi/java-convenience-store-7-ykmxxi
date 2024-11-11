package store.domain.stock;

import java.util.LinkedHashMap;
import java.util.Map;

import store.domain.product.Product;

public class ProductStorage {

    private static final Map<Product, ProductStock> PRODUCT_STORAGE = new LinkedHashMap<>();

    public void saveAll(final Iterable<ProductStock> productStocks) {
        for (ProductStock productStock : productStocks) {
            PRODUCT_STORAGE.put(productStock.product(), productStock);
        }
    }

    public ProductStock find(final Product product) {
        if (!PRODUCT_STORAGE.containsKey(product)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        return PRODUCT_STORAGE.get(product);
    }

    public boolean hasEnoughPromotionStock(final Product product, final int minPromotionQuantity) {
        ProductStock productStock = PRODUCT_STORAGE.get(product);
        return productStock.hasEnoughPromotionStock(minPromotionQuantity);
    }

    public boolean hasEnoughTotalStock(final Product product, final int orderQuantity) {
        ProductStock productStock = PRODUCT_STORAGE.get(product);
        return productStock.isEnough(orderQuantity);
    }

    public boolean isPromotionStockShortage(final Product product, final int orderQuantity) {
        ProductStock productStock = PRODUCT_STORAGE.get(product);
        return productStock.isOverPromotionStock(orderQuantity);
    }

    public int currentPromotionStockQuantity(final Product product) {
        ProductStock productStock = PRODUCT_STORAGE.get(product);
        return productStock.promotionQuantity();
    }

}
