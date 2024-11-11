package store.domain.sales;

public enum OrderType {

    NORMAL_ORDER("normal"),
    PROMOTION_STOCK_SHORTAGE_ORDER("promotionStockShortage"),
    PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER("promotionOrderQuantityShortage"),
    CAN_RECEIVE_FREE_ORDER("canReceiveFree"),
    PROMOTION_ORDER("promotion");

    private final String orderTypeName;

    OrderType(final String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public boolean isPromotion() {
        return !this.equals(NORMAL_ORDER);
    }

    public boolean isNeedReOrder() {
        return this.equals(PROMOTION_STOCK_SHORTAGE_ORDER) || this.equals(CAN_RECEIVE_FREE_ORDER) ||
                this.equals(PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER);
    }

    public boolean isCanReceiveFreeOrder() {
        return this.equals(CAN_RECEIVE_FREE_ORDER);
    }

    public boolean isPromotionOrderQuantityOrder() {
        return this.equals(PROMOTION_ORDER_QUANTITY_SHORTAGE_ORDER);
    }
}
