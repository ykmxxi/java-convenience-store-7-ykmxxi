package store.presentation.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

import store.service.inventory.dto.ProductResponse;

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
        System.out.print(stringBuilder);
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

}
