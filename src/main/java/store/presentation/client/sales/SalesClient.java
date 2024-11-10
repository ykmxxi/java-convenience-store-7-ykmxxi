package store.presentation.client.sales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import store.presentation.client.sales.dto.OrderRequest;
import store.service.sales.SalesService;
import store.service.sales.dto.OrderResponse;

public class SalesClient {

    private final SalesService salesService;

    public SalesClient(final SalesService salesService) {
        this.salesService = salesService;
    }

    public List<OrderResponse> order(final String orderInput, final LocalDateTime orderCreatedAt) {
        List<OrderRequest> orderRequests = toOrderRequests(orderInput, orderCreatedAt);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequests) {
            orderResponses.add(salesService.order(orderRequest));
        }
        return orderResponses;
    }

    public void addPromotionFree(final int orderNumber) {
        salesService.addPromotionFree(orderNumber);
    }

    public void removeNormalProduct(final int orderNumber, final int removeQuantity) {
        salesService.removeNormalProduct(orderNumber, removeQuantity);
    }

    private List<OrderRequest> toOrderRequests(final String orderInput, final LocalDateTime orderCreatedAt) {
        return Arrays.stream(orderInput.split(","))
                .map(this::removeAffix)
                .map(order -> toOrderRequest(order, orderCreatedAt))
                .toList();
    }

    private String removeAffix(final String orderInput) {
        return orderInput.strip()
                .replaceAll("[\\[|\\]]", "");
    }

    private OrderRequest toOrderRequest(final String orderInput, final LocalDateTime createdAt) {
        try {
            List<String> productAndQuantity = Arrays.stream(orderInput.split("-"))
                    .toList();
            int quantity = Integer.parseInt(productAndQuantity.getLast());
            return new OrderRequest(productAndQuantity.getFirst(), quantity, createdAt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("너무 큰 수량을 입력했습니다.");
        }
    }

}
