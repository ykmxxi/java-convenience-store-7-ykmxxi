package store.domain;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public enum PromotionType {

    TWO_PLUS_ONE(List.of("탄산2+1"), 2, 1),
    ONE_PLUS_ONE(List.of("MD추천상품", "반짝할인"), 1, 1);

    private final List<String> promotionNames;
    private final int buy;
    private final int get;

    PromotionType(final List<String> promotionNames, final int buy, final int get) {
        this.promotionNames = promotionNames;
        this.buy = buy;
        this.get = get;
    }

    public static PromotionType findByName(final String promotionName) {
        return Arrays.stream(values())
                .filter(promotionType -> promotionType.promotionNames.contains(promotionName))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("%s은(는) 존재하지 않는 프로모션입니다.".formatted(promotionName)));
    }

}
