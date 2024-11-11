package store.service.sales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import store.domain.product.Product;
import store.domain.sales.Order;
import store.presentation.client.sales.dto.OrderRequest;
import store.presentation.client.sales.dto.ReOrderRequest;
import store.service.inventory.InventoryService;
import store.service.sales.dto.ReOrderResponse;
import store.service.sales.dto.ReOrderResponseType;

public class SalesService {

    private final InventoryService inventoryService;
    private final List<Order> orders = new ArrayList<>();

    public SalesService(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public List<ReOrderResponse> order(final List<OrderRequest> orderRequests) {
        for (OrderRequest orderRequest : orderRequests) {
            Product product = inventoryService.findProduct(orderRequest.productName());
            int quantity = orderRequest.quantity();
            LocalDateTime createdAt = orderRequest.createdAt();
            Order order = createOrder(product, quantity, createdAt);
            orders.add(order);
        }
        return toReOrderResponses();
    }

    public void reOrder(final List<ReOrderRequest> reOrderRequests) {
        for (ReOrderRequest reOrderRequest : reOrderRequests) {
            if (isWantFreePromotionProduct(reOrderRequest)) {
                updateOrders(reOrderRequest);
            }
            if (isWantDecreaseNonPromotionProduct(reOrderRequest)) {
                updateOrders(reOrderRequest);
            }
        }
    }

    private boolean isWantDecreaseNonPromotionProduct(final ReOrderRequest reOrderRequest) {
        return reOrderRequest.reOrderResponseType().isPromotionStockShortage() && !reOrderRequest.yesOrNo();
    }

    private boolean isWantFreePromotionProduct(final ReOrderRequest reOrderRequest) {
        return reOrderRequest.reOrderResponseType().isPromotionOrderQuantityShortage() && reOrderRequest.yesOrNo();
    }

    private void updateOrders(final ReOrderRequest reOrderRequest) {
        Order order = orders.get(reOrderRequest.orderNumber());
        Order newOrder = Order.reOrder(order, reOrderRequest.reOrderQuantity());
        orders.set(reOrderRequest.orderNumber(), newOrder);
    }

    private List<ReOrderResponse> toReOrderResponses() {
        List<ReOrderResponse> reOrderResponses = new ArrayList<>();
        for (int orderNumber = 0; orderNumber < orders.size(); orderNumber++) {
            Order order = orders.get(orderNumber);
            if (order.isReOrderType()) {
                reOrderResponses.add(toReOrderResponse(order, orderNumber));
            }
        }
        return reOrderResponses;
    }

    private ReOrderResponse toReOrderResponse(final Order order, final int orderNumber) {
        ReOrderResponseType reOrderResponseType = ReOrderResponseType.from(order.orderType());
        if (reOrderResponseType.isPromotionStockShortage()) {
            int reOrderQuantity = calculatePromotionStockShortageQuantity(order);
            return new ReOrderResponse(orderNumber, reOrderResponseType, order.productName(), reOrderQuantity);
        }
        return new ReOrderResponse(orderNumber, reOrderResponseType, order.productName(), 1);
    }

    private int calculatePromotionStockShortageQuantity(final Order order) {
        return order.quantity() -
                inventoryService.calculatePromotionQuantity(order.product(), order.promotionCount());
    }


    private void addPromotionFree(final int orderNumber) {
        Order order = orders.get(orderNumber);
        orders.set(orderNumber, Order.reOrder(order, order.quantity() + 1));
    }

    private void removeNormalProduct(final int orderNumber, final int removeQuantity) {
        Order order = orders.get(orderNumber);
        orders.set(orderNumber, Order.reOrder(order, order.quantity() - removeQuantity));
    }

    private Order createOrder(final Product product, final int orderQuantity, final LocalDateTime createdAt) {
        validateOrderQuantity(product, orderQuantity);
        if (inventoryService.canReceivePromotion(product, createdAt)) {
            int promotionCount = inventoryService.calculatePromotionCount(product, orderQuantity);
            return createPromotionTypeOrder(product, orderQuantity, promotionCount);
        }
        return Order.normal(product, orderQuantity);
    }

    private void validateOrderQuantity(final Product product, final int orderQuantity) {
        if (!inventoryService.hasEnoughStock(product, orderQuantity)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    private Order createPromotionTypeOrder(final Product product, final int orderQuantity, final int promotionCount) {
        if (inventoryService.isPromotionStockShortage(product, orderQuantity)) {
            return Order.promotionStockShortage(product, orderQuantity, promotionCount);
        }
        if (inventoryService.isPromotionOrderQuantityShortage(product, orderQuantity)) {
            return Order.promotionOrderQuantityShortage(product, orderQuantity, promotionCount);
        }
        return Order.promotion(product, orderQuantity, promotionCount);
    }

}
