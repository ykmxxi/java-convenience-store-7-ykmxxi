package store.service.sales.dto;

public enum OrderResponseType {

    NORMAL("정상 응답"),
    PROMOTION_STOCK_OVER("프로모션 재고 초과"),
    PROMOTION_N_SHORTAGE("프로모션 혜택 수량 부족");

    private final String orderResponseTypeName;

    OrderResponseType(final String orderResponseTypeName) {
        this.orderResponseTypeName = orderResponseTypeName;
    }

    public boolean isShortage() {
        return this.equals(PROMOTION_N_SHORTAGE);
    }

    public boolean isOver() {
        return this.equals(PROMOTION_STOCK_OVER);
    }
}
