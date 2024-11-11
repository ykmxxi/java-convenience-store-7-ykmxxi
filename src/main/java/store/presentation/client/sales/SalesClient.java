package store.presentation.client.sales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import store.domain.sales.MembershipDiscount;
import store.presentation.client.sales.dto.OrderRequest;
import store.presentation.client.sales.dto.ReOrderRequest;
import store.presentation.view.Command;
import store.service.sales.SalesService;
import store.service.sales.dto.PayResponse;
import store.service.sales.dto.ReOrderResponse;

public class SalesClient {

    private final SalesService salesService;
    private final MembershipDiscount membershipDiscount;

    public SalesClient(final SalesService salesService) {
        this.salesService = salesService;
        this.membershipDiscount = new MembershipDiscount();
    }

    public List<ReOrderResponse> order(final String orderInput, final LocalDateTime orderCreatedAt) {
        List<OrderRequest> orderRequests = toOrderRequests(orderInput, orderCreatedAt);
        return salesService.order(orderRequests);
    }

    public void reOrder(final List<String> reOrderCommands, final List<ReOrderResponse> reOrderResponses) {
        List<ReOrderRequest> reOrderRequests = new ArrayList<>();
        for (int index = 0; index < reOrderCommands.size(); index++) {
            boolean yesOrNo = Command.from(reOrderCommands.get(index));
            ReOrderResponse reOrderResponse = reOrderResponses.get(index);
            reOrderRequests.add(toReOrderRequest(yesOrNo, reOrderResponse));
        }
        salesService.reOrder(reOrderRequests);
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
            int quantity = validateZero(Integer.parseInt(productAndQuantity.getLast()));
            return new OrderRequest(productAndQuantity.getFirst(), quantity, createdAt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("너무 큰 수량을 입력했습니다.");
        }
    }

    private int validateZero(final int quantity) {
        if (quantity == 0) {
            throw new IllegalArgumentException("구매 수량에 0을 입력했습니다.");
        }
        return quantity;
    }

    private ReOrderRequest toReOrderRequest(final boolean yesOrNo, final ReOrderResponse reOrderResponse) {
        return new ReOrderRequest(yesOrNo, reOrderResponse.orderNumber(),
                reOrderResponse.reOrderResponseType(), reOrderResponse.reOrderQuantity());
    }

    public PayResponse pay(final String membership) {
        return salesService.pay(Command.from(membership));
    }

}
