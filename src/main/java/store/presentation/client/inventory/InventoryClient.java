package store.presentation.client.inventory;

import java.util.List;

import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.file.FileInput;
import store.service.inventory.InventoryService;

public class InventoryClient {

    private final FileInput fileInput;
    private final InventoryService inventoryService;

    public InventoryClient(final FileInput fileInput, final InventoryService inventoryService) {
        this.fileInput = fileInput;
        this.inventoryService = inventoryService;
    }

    public void saveInventory() {
        List<List<String>> productTuples = fileInput.readProductTuples();

        List<ProductStorageRequest> productStorageRequests = buildProductStorageRequests(productTuples);
        List<ProductStockStorageRequest> productStockStorageRequests = buildProductStockStorageRequests(productTuples);
        inventoryService.saveProducts(productStorageRequests);
        inventoryService.saveProductStocks(productStockStorageRequests);
    }

    private List<ProductStorageRequest> buildProductStorageRequests(final List<List<String>> productTuples) {
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

    private List<ProductStockStorageRequest> buildProductStockStorageRequests(final List<List<String>> productTuples) {
        return productTuples.stream()
                .map(this::toProductStockStorageRequest)
                .toList();
    }

    private ProductStockStorageRequest toProductStockStorageRequest(final List<String> productTuple) {
        return new ProductStockStorageRequest(
                productTuple.get(getProductColumnIndexOf("name")),
                Integer.parseInt(productTuple.get(getProductColumnIndexOf("quantity"))),
                Integer.parseInt(productTuple.get(getProductColumnIndexOf("promotion")))
        );
    }

    private int getProductColumnIndexOf(final String columnName) {
        return fileInput.getProductColumnIndexOf(columnName);
    }

}
