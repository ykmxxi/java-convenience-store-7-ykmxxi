package store.presentation.client.inventory.dto;

import java.time.LocalDateTime;

public record PromotionStorageRequest(
        String name,
        int quantityOfBuy,
        int quantityOfFree,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
