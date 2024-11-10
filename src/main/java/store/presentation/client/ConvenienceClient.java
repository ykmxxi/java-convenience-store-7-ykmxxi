package store.presentation.client;

import java.util.List;

import store.presentation.client.inventory.InventoryClient;
import store.presentation.client.sales.SalesClient;
import store.presentation.view.InputView;
import store.presentation.view.OutputView;
import store.service.inventory.dto.ProductResponse;
import store.service.sales.dto.OrderResponse;

public class ConvenienceClient {

    private final InventoryClient inventoryClient;
    private final SalesClient salesClient;
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceClient(final InventoryClient inventoryClient, final SalesClient salesClient,
                             final InputView inputView, final OutputView outputView
    ) {
        this.inventoryClient = inventoryClient;
        this.salesClient = salesClient;
        this.inputView = inputView;
        this.outputView = outputView;
        inventoryClient.saveInventory();
    }

    public void run() {
        outputView.printWelcomeMessage();
        List<ProductResponse> productResponses = inventoryClient.getProducts();
        outputView.printProducts(productResponses);

        try {
            order();
        } catch (IllegalArgumentException | NullPointerException e) {
            outputView.printError(e.getMessage());
            order();
        }
    }

    private void order() {
        String orderInput = inputView.readOrder();
        salesClient.order(orderInput);
    }

}
