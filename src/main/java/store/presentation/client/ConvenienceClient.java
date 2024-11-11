package store.presentation.client;

import java.util.ArrayList;
import java.util.List;

import camp.nextstep.edu.missionutils.DateTimes;
import store.presentation.client.inventory.InventoryClient;
import store.presentation.client.sales.SalesClient;
import store.presentation.client.sales.dto.PayRequest;
import store.presentation.view.InputView;
import store.presentation.view.OutputView;
import store.service.inventory.dto.ProductResponse;
import store.service.sales.dto.PayResponse;
import store.service.sales.dto.ReOrderResponse;

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
        printWelcomeMessageWithConvenienceStoreInfo();

        List<ReOrderResponse> reOrderResponses = order();
        reOrder(reOrderResponses);

        PayResponse payResponses = pay();
        outputView.printReceipt(payResponses);
    }

    private PayResponse pay() {
        while (true) {
            try {
                String membership = inputView.readMembershipDiscountCommand();
                return salesClient.pay(membership);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printWelcomeMessageWithConvenienceStoreInfo() {
        outputView.printWelcomeMessage();
        List<ProductResponse> productResponses = inventoryClient.getProducts();
        outputView.printProducts(productResponses);
    }

    private List<ReOrderResponse> order() {
        while (true) {
            try {
                String orderInput = inputView.readOrder();
                return salesClient.order(orderInput, DateTimes.now());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void reOrder(final List<ReOrderResponse> reOrderResponses) {
        List<String> reOrderCommands = new ArrayList<>();
        for (ReOrderResponse reOrderResponse : reOrderResponses) {
            String reOrderCommand = readReOrder(reOrderResponse);
            reOrderCommands.add(reOrderCommand);
        }
        salesClient.reOrder(reOrderCommands, reOrderResponses);
    }

    private String readReOrder(final ReOrderResponse reOrderResponse) {
        if (reOrderResponse.reOrderResponseType().isPromotionStockShortage() ||
                reOrderResponse.reOrderResponseType().isPromotionOrderQuantityShortage()
        ) {
            return readWithoutPromotionOrder(reOrderResponse);
        }
        return readPromotionFreeCommand(reOrderResponse);
    }

    private String readWithoutPromotionOrder(final ReOrderResponse reOrderResponse) {
        while (true) {
            try {
                return inputView.readWithoutPromotionCommand(reOrderResponse.productName(),
                        reOrderResponse.reOrderQuantity());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private String readPromotionFreeCommand(final ReOrderResponse reOrderResponse) {
        while (true) {
            try {
                return inputView.readPromotionFreeCommand(reOrderResponse.productName(),
                        reOrderResponse.reOrderQuantity());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

}
