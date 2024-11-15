package store.domain.promotion;

import java.time.LocalDateTime;
import java.util.Objects;

public class PromotionPeriod {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public PromotionPeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBetween(final LocalDateTime localDateTime) {
        return isEqualOrAfterStartDate(localDateTime) && isEqualOrBeforeEndDate(localDateTime);
    }

    private boolean isEqualOrAfterStartDate(final LocalDateTime localDateTime) {
        return startDate.isEqual(localDateTime) || startDate.isBefore(localDateTime);
    }

    private boolean isEqualOrBeforeEndDate(final LocalDateTime localDateTime) {
        return endDate.isEqual(localDateTime) || endDate.isAfter(localDateTime);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromotionPeriod that)) {
            return false;
        }
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "PromotionPeriod{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
