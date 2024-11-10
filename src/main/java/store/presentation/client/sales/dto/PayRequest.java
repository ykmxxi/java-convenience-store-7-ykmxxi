package store.presentation.client.sales.dto;

import java.util.List;

import store.service.sales.dto.OrderResponse;

public record PayRequest(List<OrderResponse> orderResponses, boolean membershipDiscount) {
}
