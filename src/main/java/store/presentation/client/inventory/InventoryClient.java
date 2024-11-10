package store.presentation.client.inventory;

import java.util.List;

import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductsStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;
import store.presentation.file.FileInput;
import store.service.inventory.InventoryService;

public class InventoryClient {

    private final FileInput fileInput;
    private final InventoryService inventoryService;
    private final StorageRequestConverter storageRequestConverter;

    public InventoryClient(final FileInput fileInput, final InventoryService inventoryService) {
        this.fileInput = fileInput;
        this.inventoryService = inventoryService;
        this.storageRequestConverter = new StorageRequestConverter();
    }

    public void saveInventory() {
        List<List<String>> productTuples = fileInput.readProductTuples();
        List<List<String>> promotionTuples = fileInput.readPromotionTuples();

        saveProducts(productTuples);
        saveProductStocks(productTuples);
        savePromotions(promotionTuples);
        savePromotionProducts(productTuples);
    }

    private void saveProducts(final List<List<String>> productTuples) {
        List<ProductStorageRequest> productStorageRequests =
                storageRequestConverter.toProductStorageRequests(productTuples);

        inventoryService.saveProducts(productStorageRequests);
    }

    private void saveProductStocks(final List<List<String>> productTuples) {
        List<ProductStockStorageRequest> productStockStorageRequests =
                storageRequestConverter.toProductStockStorageRequests(productTuples);

        inventoryService.saveProductStocks(productStockStorageRequests);
    }

    private void savePromotions(final List<List<String>> promotionTuples) {
        List<PromotionStorageRequest> promotionStorageRequests =
                storageRequestConverter.toPromotionStorageRequests(promotionTuples);
        inventoryService.savePromotions(promotionStorageRequests);
    }

    private void savePromotionProducts(final List<List<String>> productTuples) {
        List<PromotionProductsStorageRequest> promotionProductsStorageRequests =
                storageRequestConverter.toPromotionProductStorageRequests(productTuples);

        inventoryService.savePromotionProducts(promotionProductsStorageRequests);
    }

}
