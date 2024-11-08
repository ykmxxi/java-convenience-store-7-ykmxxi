package store.presentation.client.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.file.FileInput;
import store.service.inventory.InventoryService;

public class InventoryClient {

    private static final Map<String, Integer> COLUMN_INDEX = new HashMap<>();

    private final FileInput fileInput;
    private final InventoryService inventoryService;

    public InventoryClient(final FileInput fileInput, final InventoryService inventoryService) {
        this.fileInput = fileInput;
        this.inventoryService = inventoryService;
    }

    public void saveInventory() {
        List<String> productLines = fileInput.readProducts();
        saveColumnIndex(productLines.getFirst());

        List<ProductStorageRequest> productStorageRequests = buildProductStorageRequests(productLines);
        List<ProductStockStorageRequest> productStockStorageRequests = buildProductStockStorageRequests(productLines);
        inventoryService.saveProducts(productStorageRequests);
        inventoryService.saveProductStocks(productStockStorageRequests);
    }

    private void saveColumnIndex(final String columnsLine) {
        List<String> columns = getColumns(columnsLine);
        IntStream.range(0, columns.size())
                .forEach(index -> COLUMN_INDEX.put(columns.get(index), index));
    }

    private List<ProductStorageRequest> buildProductStorageRequests(final List<String> productLines) {
        return productLines.stream()
                .skip(1)
                .map(this::toProductStorageRequest)
                .toList();
    }

    private ProductStorageRequest toProductStorageRequest(final String productLine) {
        List<String> productDetails = getColumns(productLine);
        return new ProductStorageRequest(
                productDetails.get(COLUMN_INDEX.get("name")),
                Long.parseLong(productDetails.get(COLUMN_INDEX.get("price")))
        );
    }

    private List<ProductStockStorageRequest> buildProductStockStorageRequests(final List<String> productLines) {
        return productLines.stream()
                .skip(1)
                .map(this::toProductStockStorageRequest)
                .toList();
    }

    private ProductStockStorageRequest toProductStockStorageRequest(final String productLine) {
        List<String> productDetails = getColumns(productLine);
        return new ProductStockStorageRequest(
                productDetails.get(COLUMN_INDEX.get("name")),
                Integer.parseInt(productDetails.get(COLUMN_INDEX.get("quantity"))),
                Integer.parseInt(productDetails.get(COLUMN_INDEX.get("promotion")))
        );
    }

    private List<String> getColumns(final String productLine) {
        List<String> productDetails = Arrays.stream(productLine.split(","))
                .toList();
        validateColumnCount(productDetails.size());
        return productDetails;
    }

    private void validateColumnCount(final int productDetailsCount) {
        if (countOfColumn() != productDetailsCount) {
            throw new IllegalStateException("상품 목록 파일에 누락된 정보가 있습니다.");
        }
    }

    private int countOfColumn() {
        return COLUMN_INDEX.keySet()
                .size();
    }

}
