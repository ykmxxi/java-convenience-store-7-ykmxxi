package store.service.sales.dto;

public record BuyingProductResponse(
        String productName,
        int quantity,
        long totalPrice
) {
}
