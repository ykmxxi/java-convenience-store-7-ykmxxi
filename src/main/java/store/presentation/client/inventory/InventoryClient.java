package store.presentation.client.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

        List<ProductStorageRequest> productStorageRequests = createRequest(productLines);
        inventoryService.saveProducts(productStorageRequests);
    }

    private List<ProductStorageRequest> createRequest(final List<String> productLines) {
        List<ProductStorageRequest> productStorageRequests = new ArrayList<>();

        for (int lineNumber = 1; lineNumber < productLines.size(); lineNumber++) {
            List<String> productDetails = getProductDetails(productLines.get(lineNumber));
            productStorageRequests.add(toProductStorageRequest(productDetails));
        }

        return productStorageRequests;
    }

    private ProductStorageRequest toProductStorageRequest(final List<String> productDetails) {
        return new ProductStorageRequest(
                productDetails.get(COLUMN_INDEX.get("name")),
                Long.parseLong(productDetails.get(COLUMN_INDEX.get("price")))
        );
    }

    private List<String> getProductDetails(final String productLine) {
        List<String> productDetails = Arrays.stream(productLine.split(","))
                .toList();
        validateColumnCount(productDetails.size());
        return productDetails;
    }

    private void saveColumnIndex(final String columns) {
        List<String> split = Arrays.stream(columns.split(","))
                .toList();
        IntStream.range(0, split.size())
                .forEach(index -> COLUMN_INDEX.put(split.get(index), index));
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
