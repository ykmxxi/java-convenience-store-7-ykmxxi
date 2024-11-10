package store.domain.sales;

public enum OrderType {

    NORMAL_ORDER("normal"),
    PROMOTION_ORDER("promotion");

    private final String orderTypeName;

    OrderType(final String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String typeName() {
        return this.orderTypeName;
    }

    public boolean isPromotion() {
        return this.equals(PROMOTION_ORDER);
    }

}
