package store.service.sales.dto;

public record ReOrderResponse(
        int orderNumber,
        ReOrderResponseType reOrderResponseType,
        String productName,
        int reOrderQuantity
) {
}
