package store.service.sales.dto;

import store.domain.sales.OrderType;

public enum ReOrderResponseType {

    PROMOTION_STOCK_SHORTAGE("프로모션 재고 초과"),
    PROMOTION_ORDER_QUANTITY_SHORTAGE("프로모션 혜택 수량 부족");

    private final String reOrderResponseType;

    ReOrderResponseType(final String reOrderResponseType) {
        this.reOrderResponseType = reOrderResponseType;
    }

    public static ReOrderResponseType from(final OrderType orderType) {
        if (orderType.isPromotionOrderQuantityShortage()) {
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

}
