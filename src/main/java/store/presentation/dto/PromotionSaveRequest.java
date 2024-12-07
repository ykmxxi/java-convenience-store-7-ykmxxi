package store.presentation.dto;

import java.time.LocalDate;

public record PromotionSaveRequest(String name, LocalDate startDate, LocalDate endDate) {

    public static PromotionSaveRequest of(final String name, final String startDate, final String endDate) {
        return new PromotionSaveRequest(name, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

}
