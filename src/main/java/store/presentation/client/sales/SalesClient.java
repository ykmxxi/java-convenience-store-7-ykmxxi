package store.presentation.client.sales;

import java.util.Arrays;
import java.util.List;

import store.presentation.client.sales.dto.OrderRequest;
import store.service.sales.SalesService;

public class SalesClient {

    private final SalesService salesService;

    public SalesClient(final SalesService salesService) {
        this.salesService = salesService;
    }

    public void order(final String orderInput) {
        List<OrderRequest> orderRequests = Arrays.stream(orderInput.split(","))
                .map(this::removeAffix)
                .map(this::toOrderRequest)
                .toList();
        orderRequests.forEach(salesService::order);
    }

    private String removeAffix(final String orderInput) {
        return orderInput.strip()
                .replaceAll("[\\[|\\]]", "");
    }

    private OrderRequest toOrderRequest(final String orderInput) {
        try {
            List<String> productAndQuantity = Arrays.stream(orderInput.split("-"))
                    .toList();
            int quantity = Integer.parseInt(productAndQuantity.getLast());
            return new OrderRequest(productAndQuantity.getFirst(), quantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("너무 큰 수량을 입력했습니다.");
        }
    }

}
