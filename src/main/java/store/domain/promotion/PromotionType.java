package store.domain.promotion;

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
                .get();
    }

    public int calculateQuantityForGetFree() {
        return quantityOfBuy + quantityOfFree;
    }

    public boolean isOrderQuantityShortage(final int orderQuantity) {
        if (this.equals(CARBONATED_TWO_PLUS_ONE)) {
            return (orderQuantity % this.calculateQuantityForGetFree()) == 1;
        }
        return false;
    }

    public String getName() {
        return name;
    }

}
