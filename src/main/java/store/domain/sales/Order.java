package store.domain.sales;

import store.domain.product.Product;

public class Order {

    private final OrderType orderType;
    private final Product product;
    private final int quantity;
    private final int promotionCount;

    private Order(final OrderType orderType, final Product product, final int quantity, final int promotionCount) {
        this.orderType = orderType;
        this.product = product;
        this.quantity = quantity;
        this.promotionCount = promotionCount;
    }

    public static Order promotion(final Product product, final int quantity, final int promotionCount) {
        return new Order(OrderType.PROMOTION_ORDER, product, quantity, promotionCount);
    }

    public static Order normal(final Product product, final int quantity) {
        return new Order(OrderType.NORMAL_ORDER, product, quantity, 0);
    }

    public static Order promotionStockShortage(final Product product, final int quantity, final int promotionCount) {
        return new Order(OrderType.PROMOTION_STOCK_SHORTAGE_ORDER, product, quantity, promotionCount);
    }

    public static Order promotionOrderQuantityShortage(final Product product, final int quantity,
                                                       final int promotionCount
    ) {
        return new Order(OrderType.PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER, product, quantity, promotionCount);
    }

    public static Order reOrder(final Order order, final int newQuantity) {
        if (order.orderType.isPromotionOrderQuantityShortage()) {
            return Order.promotion(order.product, order.quantity() + newQuantity, order.promotionCount + newQuantity);
        }
        return Order.promotion(order.product, order.quantity - newQuantity, order.promotionCount);
    }

    public boolean isReOrderType() {
        return this.orderType.isNeedReOrder();
    }

    public Product product() {
        return product;
    }

    public String productName() {
        return product.name()
                .value();
    }

    public int quantity() {
        return quantity;
    }

    public OrderType orderType() {
        return this.orderType;
    }

    public int promotionCount() {
        return promotionCount;
    }

}
