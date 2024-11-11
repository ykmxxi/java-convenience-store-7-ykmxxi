package store.presentation.client.sales.dto;

import java.util.List;

import store.service.sales.dto.ReOrderResponse;

public record PayRequest(boolean membershipDiscount) {
}
