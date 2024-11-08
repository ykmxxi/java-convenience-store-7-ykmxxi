package store.repository.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.repository.Repository;

public class ProductStockRepository implements Repository<ProductStock, Product> {

    private static final Map<Product, ProductStock> PRODUCT_STOCK_INVENTORY = new HashMap<>();

    @Override
    public ProductStock find(final Product product) {
        if (PRODUCT_STOCK_INVENTORY.containsKey(product)) {
            return PRODUCT_STOCK_INVENTORY.get(product);
        }
        throw new IllegalArgumentException("존재하지 않는 상품입니다.");
    }

    @Override
    public List<ProductStock> saveAll(final Iterable<ProductStock> entities) {
        List<ProductStock> productStocks = new ArrayList<>();
        for (ProductStock entity : entities) {
            PRODUCT_STOCK_INVENTORY.put(entity.product(), entity);
            productStocks.add(entity);
        }
        return productStocks;
    }

}
