package store.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Products {

    private final Set<Product> products = new HashSet<>();

    public void save(final Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products.stream()
                .toList();
    }

    public Product findByName(final String productName) {
        return products.stream()
                .filter(product -> product.isSameName(productName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

}
