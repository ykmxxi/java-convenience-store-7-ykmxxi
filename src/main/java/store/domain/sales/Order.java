package store.domain.sales;

import store.domain.inventory.Product;

public class Order {

    private final OrderType orderType;
    private final Product product;
    private final int quantity;

    private Order(final OrderType orderType, final Product product, final int quantity) {
        this.orderType = orderType;
        this.product = product;
        this.quantity = quantity;
    }

    public static Order promotion(final Product product, final int quantity) {
        return new Order(OrderType.PROMOTION_ORDER, product, quantity);
    }

    public static Order normal(final Product product, final int quantity) {
        return new Order(OrderType.NORMAL_ORDER, product, quantity);
    }

    public static Order reOrder(final Order order, final int newQuantity) {
        return new Order(order.orderType, order.product, newQuantity);
    }

    public boolean isPromotionType() {
        return this.orderType.isPromotion();
    }

    public String typeName() {
        return orderType.typeName();
    }

    public String productName() {
        return product.name()
                .value();
    }

    public int quantity() {
        return quantity;
    }

}
