package store.presentation.view;

import java.util.regex.Pattern;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final Pattern ORDER_PATTERN = Pattern.compile(
            "^(\\[[ㄱ-ㅎ가-힣a-zA-Z]+-\\d+],\\s?)*(\\[[ㄱ-ㅎ가-힣a-zA-Z]+-\\d+])$");

    public String readOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String orderInput = Console.readLine();
        validateNull(orderInput);
        validateBlank(orderInput);
        validateInputPattern(orderInput);
        return orderInput;
    }

    private void validateNull(final String input) {
        if (input == null) {
            throw new IllegalStateException("콘솔에 문제가 생겨 프로그램을 종료합니다. 다시 실행해 주세요.");
        }
    }

    private void validateBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("아무것도 입력하지 않으시거나 공백만 입력했습니다.");
        }
    }

    private void validateInputPattern(final String orderInput) {
        if (!ORDER_PATTERN.matcher(orderInput)
                .matches()
        ) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }
    }

}
