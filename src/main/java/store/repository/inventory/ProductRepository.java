package store.repository.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.repository.Repository;

public class ProductRepository implements Repository<Product, Name> {

    private static final Map<Name, Product> PRODUCT_INVENTORY = new HashMap<>();

    @Override
    public List<Product> saveAll(final Iterable<Product> entities) {
        List<Product> products = new ArrayList<>();
        for (Product entity : entities) {
            PRODUCT_INVENTORY.put(entity.name(), entity);
            products.add(entity);
        }
        return products;
    }

}
