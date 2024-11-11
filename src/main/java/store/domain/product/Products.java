package store.domain.product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Products {

    private static final Map<Name, Product> PRODUCTS = new LinkedHashMap<>();

    public void saveAll(final Iterable<Product> products) {
        for (Product product : products) {
            PRODUCTS.put(product.name(), product);
        }
    }

    public Product find(final Name name) {
        if (!PRODUCTS.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        return PRODUCTS.get(name);
    }

    public List<Product> findAll() {
        return PRODUCTS.values()
                .stream()
                .toList();
    }

}
