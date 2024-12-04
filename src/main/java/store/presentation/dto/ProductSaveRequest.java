package store.presentation.dto;

public record ProductSaveRequest(String name, int price, int quantity, String promotion) {

    public static ProductSaveRequest of(
            final String name, final int price, final int quantity, final String promotion
    ) {
        return new ProductSaveRequest(name, price, quantity, promotion);
    }

}
