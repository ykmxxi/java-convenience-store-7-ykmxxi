package store.domain.sales;

public enum OrderType {

    NORMAL_ORDER("normal"),
    PROMOTION_STOCK_SHORTAGE_ORDER("promotionStockShortage"),
    PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER("promotionOrderQuantityShortage"),
    PROMOTION_ORDER("promotion");

    private final String orderTypeName;

    OrderType(final String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public boolean isPromotion() {
        return !this.equals(NORMAL_ORDER);
    }

    public boolean isNeedReOrder() {
        return this.equals(PROMOTION_STOCK_SHORTAGE_ORDER) || this.equals(PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER);
    }

    public boolean isPromotionOrderQuantityShortage() {
        return this.equals(PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER);
    }

}
