package store.presentation.client;

import java.util.List;

import store.presentation.client.inventory.InventoryClient;
import store.presentation.view.InputView;
import store.presentation.view.OutputView;
import store.service.inventory.dto.ProductResponse;

public class ConvenienceClient {

    private final InventoryClient inventoryClient;
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceClient(final InventoryClient inventoryClient, final InputView inputView,
                             final OutputView outputView) {
        this.inventoryClient = inventoryClient;
        this.inputView = inputView;
        this.outputView = outputView;
        inventoryClient.saveInventory();
    }

    public void run() {
        outputView.printWelcomeMessage();
        List<ProductResponse> productResponses = inventoryClient.getProducts();
        outputView.printProducts(productResponses);

        try {
            String orderInput = inputView.readOrder();
        } catch (IllegalArgumentException | NullPointerException e) {
            outputView.printError(e.getMessage());
        }

    }

}
