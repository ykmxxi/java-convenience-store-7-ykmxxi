package store.service.sales.dto;

public record OrderResponse(
        int orderNumber,
        OrderResponseType orderResponseType,
        String productName,
        int normalProductQuantity,
        int promotionProductQuantity,
        int freeProductQuantity
) {
}
