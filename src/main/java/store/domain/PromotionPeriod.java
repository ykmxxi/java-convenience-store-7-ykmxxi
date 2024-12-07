package store.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PromotionPeriod {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    private PromotionPeriod(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static PromotionPeriod of(final LocalDate startDate, final LocalDate endDate) {
        return new PromotionPeriod(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)
        );
    }

}
