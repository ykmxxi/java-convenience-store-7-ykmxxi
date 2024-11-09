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
        StorageRequestConverter storageRequestConverter = new StorageRequestConverter();

        saveProducts(storageRequestConverter, productTuples);
        saveProductStocks(storageRequestConverter, productTuples);
    }

    private void saveProductStocks(final StorageRequestConverter storageRequestConverter,
                                   final List<List<String>> productTuples
    ) {
        List<ProductStockStorageRequest> productStockStorageRequests =
                storageRequestConverter.toProductStockStorageRequests(productTuples);

        inventoryService.saveProductStocks(productStockStorageRequests);
    }

    private void saveProducts(final StorageRequestConverter storageRequestConverter,
                              final List<List<String>> productTuples
    ) {
        List<ProductStorageRequest> productStorageRequests =
                storageRequestConverter.toProductStorageRequests(productTuples);

        inventoryService.saveProducts(productStorageRequests);
    }

}
