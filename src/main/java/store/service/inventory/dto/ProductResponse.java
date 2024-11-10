package store.service.inventory.dto;

public record ProductResponse(String name, long price, int quantity, String promotion) {
}
