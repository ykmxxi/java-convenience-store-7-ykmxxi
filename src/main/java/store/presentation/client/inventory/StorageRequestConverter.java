package store.presentation.client.inventory;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.file.ProductColumn;

public class StorageRequestConverter {

    private static final String NORMAL = "null";

    public List<ProductStorageRequest> toProductStorageRequests(final List<List<String>> productTuples) {
        return productTuples.stream()
                .map(this::toProductStorageRequest)
                .toList();
    }

    private ProductStorageRequest toProductStorageRequest(final List<String> productTuple) {
        return new ProductStorageRequest(
                productTuple.get(getProductColumnIndexOf("name")),
                Long.parseLong(productTuple.get(getProductColumnIndexOf("price")))
        );
    }

    public List<ProductStockStorageRequest> toProductStockStorageRequests(final List<List<String>> productTuples) {
        Set<String> productNames = extractProductNames(productTuples);
        Map<String, Integer> normalQuantities = new LinkedHashMap<>();
        Map<String, Integer> promotionQuantities = new LinkedHashMap<>();
        productTuples.forEach(tuple -> saveQuantities(tuple, normalQuantities, promotionQuantities));
        return productNames.stream()
                .map(name -> toProductStockStorageRequest(name, normalQuantities.getOrDefault(name, 0),
                        promotionQuantities.getOrDefault(name, 0))
                ).toList();
    }

    private Set<String> extractProductNames(final List<List<String>> productTuples) {
        Set<String> productNames = new LinkedHashSet<>();
        productTuples.forEach(tuple ->
                productNames.add(tuple.get(getProductColumnIndexOf("name")))
        );
        return productNames;
    }

    private void saveQuantities(final List<String> productTuple,
                                final Map<String, Integer> normalQuantities,
                                final Map<String, Integer> promotionQuantities) {
        if (isNormalProduct(productTuple)) {
            saveQuantity(productTuple, normalQuantities);
            return;
        }
        saveQuantity(productTuple, promotionQuantities);
    }

    private void saveQuantity(final List<String> productTuple, final Map<String, Integer> quantities) {
        quantities.put(
                productTuple.get(getProductColumnIndexOf("name")),
                Integer.parseInt(productTuple.get(getProductColumnIndexOf("quantity")))
        );
    }

    private boolean isNormalProduct(final List<String> productTuple) {
        return NORMAL.equals(productTuple.get(getProductColumnIndexOf("promotion")));
    }

    private ProductStockStorageRequest toProductStockStorageRequest(final String name,
                                                                    final int normalQuantity,
                                                                    final int promotionQuantity) {
        return new ProductStockStorageRequest(name, normalQuantity, promotionQuantity);
    }

    private int getProductColumnIndexOf(final String columnName) {
        return ProductColumn.index(columnName);
    }

}
