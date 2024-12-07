package store.domain;

import java.util.HashMap;
import java.util.Map;

public class ProductStock {

    private final Map<Product, Stock> productStocks = new HashMap<>();

    public void saveStock(final Product product, final Stock stock) {
        productStocks.put(product, stock);
    }

    public Stock findStock(final String productName) {
        for (Product product : productStocks.keySet()) {
            if (product.isSameName(productName)) {
                return productStocks.get(product);
            }
        }
        throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해주세요.");
    }

}
