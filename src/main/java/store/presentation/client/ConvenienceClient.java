package store.presentation.client;

import java.util.List;

import camp.nextstep.edu.missionutils.DateTimes;
import store.presentation.client.inventory.InventoryClient;
import store.presentation.client.sales.SalesClient;
import store.presentation.view.Command;
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
        printWelcomeMessageWithConvenienceStoreInfo();

        List<OrderResponse> orderResponses = reOrder(order());
        String membershipDiscountAmount = "";
        if (applyMembershipDiscount()) {
            membershipDiscountAmount = salesClient.applyMemberShipDiscount(orderResponses);
        }
    }

    private void printWelcomeMessageWithConvenienceStoreInfo() {
        outputView.printWelcomeMessage();
        List<ProductResponse> productResponses = inventoryClient.getProducts();
        outputView.printProducts(productResponses);
    }

    private List<OrderResponse> order() {
        while (true) {
            try {
                String orderInput = inputView.readOrder();
                return salesClient.order(orderInput, DateTimes.now());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private List<OrderResponse> reOrder(final List<OrderResponse> orderResponses) {
        for (OrderResponse orderResponse : orderResponses) {
            if (orderResponse.orderResponseType().isShortage()) {
                addPromotionFree(orderResponse);
            }
            if (orderResponse.orderResponseType().isOver()) {
                removeNormalProduct(orderResponse);
            }
        }
        return salesClient.reOrder();
    }

    private void addPromotionFree(final OrderResponse orderResponse) {
        String promotionFreeCommand = readPromotionFreeCommand(orderResponse);
        if (Command.isYes(promotionFreeCommand)) {
            salesClient.addPromotionFree(orderResponse.orderNumber());
        }
    }

    private void removeNormalProduct(final OrderResponse orderResponse) {
        String withoutPromotionCommand = readWithoutPromotionCommand(orderResponse);
        if (Command.isNo(withoutPromotionCommand)) {
            salesClient.removeNormalProduct(orderResponse.orderNumber(), orderResponse.normalProductQuantity());
        }
    }

    private String readPromotionFreeCommand(final OrderResponse orderResponse) {
        while (true) {
            try {
                return inputView.readPromotionFreeCommand(orderResponse.productName());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private String readWithoutPromotionCommand(final OrderResponse orderResponse) {
        while (true) {
            try {
                return inputView.readWithoutPromotionCommand(orderResponse.productName(),
                        orderResponse.normalProductQuantity());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private boolean applyMembershipDiscount() {
        while (true) {
            try {
                String membershipDiscountCommand = inputView.readMembershipDiscountCommand();
                return Command.isYes(membershipDiscountCommand);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

}
