package store.domain.inventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum PromotionType {

    CARBONATED_TWO_PLUS_ONE("탄산2+1", 2, 1,
            LocalDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.MIDNIGHT),
            LocalDateTime.of(LocalDate.parse("2024-12-31"), LocalTime.MAX)
    ),
    MD_RECOMMENDED_PRODUCT("MD추천상품", 1, 1,
            LocalDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.MIDNIGHT),
            LocalDateTime.of(LocalDate.parse("2024-12-31"), LocalTime.MAX)
    ),
    FLASH_DISCOUNT("반짝할인", 1, 1,
            LocalDateTime.of(LocalDate.parse("2024-11-01"), LocalTime.MIDNIGHT),
            LocalDateTime.of(LocalDate.parse("2024-11-30"), LocalTime.MAX)
    );

    private final String name;
    private final int quantityOfBuy;
    private final int quantityOfFree;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    PromotionType(final String name, final int quantityOfBuy, final int quantityOfFree,
                  final LocalDateTime startDate, final LocalDateTime endDate
    ) {
        this.name = name;
        this.quantityOfBuy = quantityOfBuy;
        this.quantityOfFree = quantityOfFree;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PromotionType from(final String name) {
        return Arrays.stream(PromotionType.values())
                .filter(promotionType -> promotionType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 행사입니다."));
    }

    public void validateBuyNGetOneFree(final int quantityOfBuy, final int quantityOfFree) {
        if (isInvalidQuantity(quantityOfBuy, quantityOfFree)) {
            throw new IllegalStateException("해당 행사에 맞지 않는 수량입니다.");
        }
    }

    private boolean isInvalidQuantity(final int quantityOfBuy, final int quantityOfFree) {
        return this.quantityOfBuy != quantityOfBuy ||
                this.quantityOfFree != quantityOfFree;
    }

    public void validatePeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (!this.startDate.isEqual(startDate) || !this.endDate.isEqual(endDate)) {
            throw new IllegalStateException("해당 행사 기간과 다릅니다.");
        }
    }

    public int calculateQuantityForGetFree() {
        return quantityOfBuy + quantityOfFree;
    }

    public String getName() {
        return name;
    }
}
