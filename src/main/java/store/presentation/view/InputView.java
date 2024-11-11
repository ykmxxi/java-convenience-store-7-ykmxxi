package store.presentation.view;

import java.util.regex.Pattern;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final Pattern ORDER_PATTERN = Pattern.compile(
            "^(\\[[ㄱ-ㅎ가-힣a-zA-Z]+-\\d+],\\s?)*(\\[[ㄱ-ㅎ가-힣a-zA-Z]+-\\d+])$");
    private static final Pattern COMMAND_PATTERN = Pattern.compile("[YyNn]+");

    public String readOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String orderInput = readLine();
        validateInputPattern(orderInput);
        return orderInput;
    }

    public String readPromotionFreeCommand(final String productName, final int reOrderQuantity) {
        System.out.println();
        System.out.println("현재 " + productName + "은(는)" + reOrderQuantity + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return readCommand();
    }

    public String readWithoutPromotionCommand(final String productName, final int reOrderQuantity) {
        System.out.println();
        System.out.println(
                "현재 " + productName + " " + reOrderQuantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return readCommand();
    }

    public String readMembershipDiscountCommand() {
        System.out.println();
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return readCommand();
    }

    private String readLine() {
        String inputLine = Console.readLine();
        validateNull(inputLine);
        validateBlank(inputLine);
        return inputLine;
    }

    private String readCommand() {
        String inputLine = readLine();
        validateGuideCommand(inputLine);
        return inputLine.toUpperCase();
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

    private void validateGuideCommand(final String commandInput) {
        if (!COMMAND_PATTERN.matcher(commandInput)
                .matches()
        ) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

}
