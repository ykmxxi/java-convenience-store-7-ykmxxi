package store.service.sales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import store.domain.product.Product;
import store.domain.sales.MembershipDiscount;
import store.domain.sales.Order;
import store.presentation.client.sales.dto.OrderRequest;
import store.presentation.client.sales.dto.ReOrderRequest;
import store.service.inventory.InventoryService;
import store.service.sales.dto.BuyingProductResponse;
import store.service.sales.dto.PayResponse;
import store.service.sales.dto.PromotionProductResponse;
import store.service.sales.dto.ReOrderResponse;
import store.service.sales.dto.ReOrderResponseType;
import store.service.sales.dto.TotalAmountResponse;

public class SalesService {

    private final InventoryService inventoryService;
    private final List<Order> orders = new ArrayList<>();
    private final MembershipDiscount membershipDiscount;

    public SalesService(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.membershipDiscount = new MembershipDiscount();
    }

    public List<ReOrderResponse> order(final List<OrderRequest> orderRequests) {
        orders.clear();
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

    private boolean isWantFreePromotionProduct(final ReOrderRequest reOrderRequest) {
        return reOrderRequest.reOrderResponseType().isCanReceiveFree() && reOrderRequest.yesOrNo();
    }

    private boolean isWantDecreaseNonPromotionProduct(final ReOrderRequest reOrderRequest) {
        return reOrderRequest.reOrderResponseType().isPromotionStockShortage() && !reOrderRequest.yesOrNo() ||
                reOrderRequest.reOrderResponseType().isPromotionOrderQuantityShortage() && !reOrderRequest.yesOrNo();
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
        if (reOrderResponseType.isPromotionStockShortage() || reOrderResponseType.isPromotionOrderQuantityShortage()) {
            int reOrderQuantity = calculatePromotionStockShortageQuantity(order);
            return new ReOrderResponse(orderNumber, reOrderResponseType, order.productName(), reOrderQuantity);
        }
        return new ReOrderResponse(orderNumber, reOrderResponseType, order.productName(), 1);
    }

    private int calculatePromotionStockShortageQuantity(final Order order) {
        return order.quantity() -
                inventoryService.calculatePromotionQuantity(order.product(), order.promotionCount());
    }

    private Order createOrder(final Product product, final int orderQuantity, final LocalDateTime createdAt) {
        validateOrderQuantity(product, orderQuantity);
        if (inventoryService.canReceivePromotion(product, createdAt)) {
            if (inventoryService.isOrderQuantityShortage(product, orderQuantity)) {
                return Order.quantityShortage(product, orderQuantity, getPromotionCount(product, orderQuantity));
            }
            return createPromotionTypeOrder(product, orderQuantity, getPromotionCount(product, orderQuantity));
        }
        return Order.normal(product, orderQuantity);
    }

    private int getPromotionCount(final Product product, final int orderQuantity) {
        return inventoryService.calculatePromotionCount(product, orderQuantity);
    }

    private void validateOrderQuantity(final Product product, final int orderQuantity) {
        if (!inventoryService.hasEnoughStock(product, orderQuantity)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    private Order createPromotionTypeOrder(final Product product, final int orderQuantity, final int promotionCount) {
        if (inventoryService.isPromotionStockShortage(product, orderQuantity, promotionCount)) {
            return Order.stockShortage(product, orderQuantity, promotionCount);
        }
        if (inventoryService.isCanReceiveFree(product, orderQuantity, promotionCount)) {
            return Order.canReceiveFree(product, orderQuantity, promotionCount);
        }
        return Order.promotion(product, orderQuantity, promotionCount);
    }

    public PayResponse pay(final boolean applyMembership) {
        inventoryService.decreaseProductStock(orders);
        return toPayResponse(applyMembership);
    }

    private PayResponse toPayResponse(final boolean applyMembership) {
        List<BuyingProductResponse> buyingProductResponses = toBuyingProductResponses();
        List<PromotionProductResponse> promotionProductResponses = toPromotionProductResponses();
        TotalAmountResponse totalAmountResponse = calculateTotalBuyingAmount();
        long promotionAmount = calculatePromotionAmount();
        long membershipAmount = calculateMembershipAmount(applyMembership);
        long totalPayAmount = totalAmountResponse.totalAmount() - promotionAmount - membershipAmount;
        return new PayResponse(buyingProductResponses, promotionProductResponses, totalAmountResponse,
                promotionAmount, membershipAmount, totalPayAmount);
    }

    private List<BuyingProductResponse> toBuyingProductResponses() {
        return orders.stream().
                filter(order -> order.quantity() != 0)
                .map(this::toBuyingProductResponse)
                .toList();
    }

    private BuyingProductResponse toBuyingProductResponse(final Order order) {
        return new BuyingProductResponse(order.productName(), order.quantity(),
                order.quantity() * order.product().price());
    }

    private List<PromotionProductResponse> toPromotionProductResponses() {
        return orders.stream()
                .filter(Order::hasPromotionFree)
                .map(order -> new PromotionProductResponse(order.productName(), order.promotionCount()))
                .toList();
    }

    private TotalAmountResponse calculateTotalBuyingAmount() {
        int totalQuantity = orders.stream()
                .mapToInt(Order::quantity)
                .sum();
        long totalBuyingAmount = orders.stream()
                .mapToLong(order -> order.product().price() * order.quantity())
                .sum();
        return new TotalAmountResponse(totalQuantity, totalBuyingAmount);
    }

    private long calculatePromotionAmount() {
        return orders.stream()
                .mapToLong(order -> order.product().price() * order.promotionCount())
                .sum();
    }

    private long calculateNormalProductTotalPrice() {
        long normalProductTotalPrice = 0L;
        for (Order order : orders) {
            if (order.isNormalType()) {
                normalProductTotalPrice += order.product().price() * order.quantity();
                continue;
            }
            normalProductTotalPrice = calculateWithoutPromotionProduct(order, normalProductTotalPrice);
        }
        return normalProductTotalPrice;
    }

    private long calculateWithoutPromotionProduct(final Order order, long normalProductTotalPrice) {
        int promotionQuantity = inventoryService.calculatePromotionQuantity(order.product(), order.promotionCount());
        normalProductTotalPrice += (order.quantity() - promotionQuantity) * order.product().price();
        return normalProductTotalPrice;
    }

    private long calculateMembershipAmount(final boolean applyMembership) {
        if (applyMembership) {
            return membershipDiscount.calculateDiscountAmount(calculateNormalProductTotalPrice());
        }
        return 0L;
    }

}
