package store.presentation.client.sales.dto;

import java.time.LocalDateTime;

public record OrderRequest(String productName, int quantity, LocalDateTime createdAt) {
}
