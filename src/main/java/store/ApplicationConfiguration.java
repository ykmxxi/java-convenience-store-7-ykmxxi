package store;

import store.presentation.client.inventory.InventoryClient;
import store.presentation.client.sales.SalesClient;
import store.presentation.file.FileInput;
import store.presentation.view.InputView;
import store.presentation.view.OutputView;
import store.service.inventory.InventoryService;
import store.service.sales.SalesService;

public class ApplicationConfiguration {

    public InventoryClient inventoryClient() {
        return new InventoryClient(fileInput(), inventoryService());
    }

    public SalesClient orderClient() {
        return new SalesClient(salesService());
    }

    private SalesService salesService() {
        return new SalesService(inventoryService());
    }

    private InventoryService inventoryService() {
        return new InventoryService();
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    private FileInput fileInput() {
        return new FileInput();
    }

}
