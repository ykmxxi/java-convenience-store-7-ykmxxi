package store.presentation.client.inventory.dto;

public record ProductStockStorageRequest(
        String name,
        int normalQuantity,
        int promotionQuantity
) {
}
