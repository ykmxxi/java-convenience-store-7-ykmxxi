package store.service.sales;

import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.sales.Order;
import store.presentation.client.sales.dto.OrderRequest;
import store.service.inventory.InventoryService;

public class SalesService {

    private final InventoryService inventoryService;

    public SalesService(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void order(OrderRequest orderRequest) {
        Product product = inventoryService.findProduct(orderRequest.productName());
        int quantity = orderRequest.quantity();
        Order order = createOrder(product, quantity);
    }

    private Order createOrder(final Product product, final int quantity) {
        validateOrderQuantity(product, quantity);
        if (inventoryService.isPromotionProduct(product)) {
            return Order.promotion(product, quantity);
        }
        return Order.normal(product, quantity);
    }

    private void validateOrderQuantity(final Product product, final int quantity) {
        ProductStock productStock = inventoryService.findProductStock(product);
        if (productStock.isEnough(quantity)) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

}
