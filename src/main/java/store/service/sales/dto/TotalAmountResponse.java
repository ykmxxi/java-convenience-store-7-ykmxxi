package store.service.sales.dto;

public record TotalAmountResponse(
        int totalQuantity,
        long totalAmount
) {
}
