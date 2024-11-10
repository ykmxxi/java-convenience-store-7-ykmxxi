package store.service.sales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.PromotionProduct;
import store.domain.sales.Order;
import store.presentation.client.sales.dto.OrderRequest;
import store.service.inventory.InventoryService;
import store.service.sales.dto.OrderResponse;
import store.service.sales.dto.OrderResponseType;

public class SalesService {

    private final InventoryService inventoryService;
    private final List<Order> orders = new ArrayList<>();

    public SalesService(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public OrderResponse order(final OrderRequest orderRequest) {
        Product product = inventoryService.findProduct(orderRequest.productName());
        int quantity = orderRequest.quantity();
        LocalDateTime createdAt = orderRequest.createdAt();
        Order order = createOrder(product, quantity, createdAt);
        orders.add(order);
        return toOrderResponse(getOrderNumber(), product, order);
    }

    public void addPromotionFree(final int orderNumber) {
        Order order = orders.get(orderNumber);
        orders.set(orderNumber, Order.reOrder(order, order.quantity() + 1));
    }

    public void removeNormalProduct(final int orderNumber, final int removeQuantity) {
        Order order = orders.get(orderNumber);
        orders.set(orderNumber, Order.reOrder(order, order.quantity() - removeQuantity));
    }

    private Order createOrder(final Product product, final int quantity, final LocalDateTime createdAt) {
        validateOrderQuantity(product, quantity);
        if (inventoryService.canReceivePromotion(product, createdAt)) {
            return Order.promotion(product, quantity);
        }
        return Order.normal(product, quantity);
    }

    private void validateOrderQuantity(final Product product, final int quantity) {
        ProductStock productStock = inventoryService.findProductStock(product);
        if (!productStock.isEnough(quantity)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    private OrderResponse toOrderResponse(final int orderNumber, final Product product, final Order order) {
        if (order.isPromotionType()) {
            int promotionCount = inventoryService.calculatePromotionCount(product, order.quantity());
            int promotionProductCount = inventoryService.calculatePromotionProductCount(product, promotionCount);
            PromotionProduct promotionProduct = inventoryService.findPromotionProduct(product);
            if (promotionProduct.isShortageOrder(promotionCount, order.quantity())) {
                return toPromotionOrderResponse(orderNumber, OrderResponseType.PROMOTION_N_SHORTAGE, order,
                        promotionProductCount, promotionCount);
            }
            if (promotionProduct.isOverOrder(promotionCount, order.quantity())) {
                return toPromotionOrderResponse(orderNumber, OrderResponseType.PROMOTION_STOCK_OVER, order,
                        promotionProductCount, promotionCount);
            }
            return toPromotionOrderResponse(orderNumber, OrderResponseType.NORMAL, order, promotionProductCount,
                    promotionCount);
        }
        return new OrderResponse(orderNumber, OrderResponseType.NORMAL, order.productName(), order.quantity(), 0, 0);
    }

    private int getOrderNumber() {
        return orders.size() - 1;
    }

    private OrderResponse toPromotionOrderResponse(final int orderNumber, final OrderResponseType orderResponseType,
                                                   final Order order, final int promotionProductCount,
                                                   final int promotionCount
    ) {
        return new OrderResponse(orderNumber, orderResponseType, order.productName(),
                order.quantity() - promotionProductCount, promotionProductCount, promotionCount);
    }

}
