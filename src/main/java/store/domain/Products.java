package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products = new ArrayList<>();

    public void save(final Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products.stream()
                .toList();
    }

}
