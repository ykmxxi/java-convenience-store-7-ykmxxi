package store.presentation.client.sales.dto;

import store.service.sales.dto.ReOrderResponseType;

public record ReOrderRequest(
        boolean yesOrNo,
        int orderNumber,
        ReOrderResponseType reOrderResponseType,
        int reOrderQuantity
) {
}
