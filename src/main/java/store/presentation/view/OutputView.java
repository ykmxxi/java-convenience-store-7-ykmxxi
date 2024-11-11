package store.presentation.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

import store.service.inventory.dto.ProductResponse;
import store.service.sales.dto.BuyingProductResponse;
import store.service.sales.dto.PayResponse;
import store.service.sales.dto.PromotionProductResponse;

public class OutputView {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.KOREA);
    private static final String NEW_LINE = System.lineSeparator();

    public void printWelcomeMessage() {
        System.out.println("""
                안녕하세요. W편의점입니다.
                현재 보유하고 있는 상품입니다.
                """
        );
    }

    public void printProducts(final List<ProductResponse> productResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        productResponses.forEach(
                productResponse -> stringBuilder.append(formatProductResponse(productResponse))
        );
        System.out.println(stringBuilder);
    }

    private String formatProductResponse(final ProductResponse productResponse) {
        StringJoiner productJoiner = new StringJoiner(" ", "- ", NEW_LINE);
        productJoiner.add(productResponse.name())
                .add(formatPrice(productResponse.price()))
                .add(formatQuantity(productResponse.quantity()))
                .add(productResponse.promotion());
        return productJoiner.toString();
    }

    private String formatPrice(final long price) {
        String formattedPrice = NUMBER_FORMAT.format(price);
        return String.join("", formattedPrice, "원");
    }

    private CharSequence formatQuantity(final int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        String formattedQuantity = NUMBER_FORMAT.format(quantity);
        return String.join("", formattedQuantity, "개");
    }

    public void printError(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message, "다시 입력해 주세요."));
    }

    public void printReceipt(final PayResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
        appendReceiptStartMessage(stringBuilder);
        appendBuyingProducts(response, stringBuilder);
        appendPromotionProducts(response, stringBuilder);
        appendAmountAndDiscountInformation(response, stringBuilder);
        System.out.println(stringBuilder);
    }

    private void appendReceiptStartMessage(final StringBuilder stringBuilder) {
        stringBuilder.append("==============W 편의점================")
                .append(System.lineSeparator())
                .append("상품명               수량        금액")
                .append(System.lineSeparator());
    }

    private void appendBuyingProducts(final PayResponse response, final StringBuilder stringBuilder) {
        for (BuyingProductResponse product : response.buyingProducts()) {
            stringBuilder.append(String.format("%-15s %5s %12s" + System.lineSeparator(),
                    product.productName(),
                    NUMBER_FORMAT.format(product.quantity()),
                    NUMBER_FORMAT.format(product.totalPrice())
            ));
        }
    }

    private void appendPromotionProducts(final PayResponse response, final StringBuilder stringBuilder) {
        stringBuilder.append("=============증     정===============").append(System.lineSeparator());
        for (PromotionProductResponse promotionProductResponse : response.promotionProducts()) {
            stringBuilder.append(
                    String.format("%-15s %5s" + System.lineSeparator(), promotionProductResponse.productName(),
                            NUMBER_FORMAT.format(promotionProductResponse.quantity())
                    ));
        }
    }

    private void appendAmountAndDiscountInformation(final PayResponse response, final StringBuilder stringBuilder) {
        stringBuilder.append("====================================").append(System.lineSeparator())
                .append(String.format("%-15s %5s %12s" + System.lineSeparator(), "총구매액",
                        NUMBER_FORMAT.format(response.totalAmountResponse().totalQuantity()),
                        NUMBER_FORMAT.format(response.totalAmountResponse().totalAmount())))
                .append(String.format("%-15s %17s" + System.lineSeparator(), "행사할인",
                        NUMBER_FORMAT.format(-response.promotionAmount())))
                .append(String.format("%-15s %17s" + System.lineSeparator(), "멤버십할인",
                        NUMBER_FORMAT.format(-response.membershipAmount())))
                .append(String.format("%-15s %17s" + System.lineSeparator(), "내실돈",
                        NUMBER_FORMAT.format(response.totalPayAmount())));
    }

}
