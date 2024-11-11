package store.service.sales.dto;

import store.domain.sales.OrderType;

public enum ReOrderResponseType {

    PROMOTION_STOCK_SHORTAGE("프로모션 재고 초과"),
    PROMOTION_ORDER_QUANTITY_SHORTAGE("프로모션 혜택 수량 부족"),
    CAN_RECEIVE_FREE("1개 무료 증정 가능");

    private final String reOrderResponseType;

    ReOrderResponseType(final String reOrderResponseType) {
        this.reOrderResponseType = reOrderResponseType;
    }

    public static ReOrderResponseType from(final OrderType orderType) {
        if (orderType.isCanReceiveFreeOrder()) {
            return CAN_RECEIVE_FREE;
        }
        if (orderType.isPromotionOrderQuantityOrder()) {
            return PROMOTION_ORDER_QUANTITY_SHORTAGE;
        }
        return PROMOTION_STOCK_SHORTAGE;
    }

    public boolean isPromotionStockShortage() {
        return this.equals(PROMOTION_STOCK_SHORTAGE);
    }

    public boolean isPromotionOrderQuantityShortage() {
        return this.equals(PROMOTION_ORDER_QUANTITY_SHORTAGE);
    }

    public boolean isCanReceiveFree() {
        return this.equals(CAN_RECEIVE_FREE);
    }

}
