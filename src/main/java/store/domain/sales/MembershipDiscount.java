package store.domain.sales;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MembershipDiscount {

    private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(30L);
    private static final BigDecimal MAX_MEMBERSHIP_AMOUNT = BigDecimal.valueOf(8000L);

    private BigDecimal accumulatedAmount;

    public MembershipDiscount() {
        this.accumulatedAmount = BigDecimal.ZERO;
    }

    public long calculateDiscountAmount(final long amount) {
        BigDecimal discountAmount = DISCOUNT_RATE.multiply(BigDecimal.valueOf(amount))
                .divide(BigDecimal.valueOf(100L), MathContext.DECIMAL32)
                .setScale(0, RoundingMode.HALF_UP);
        accumulatedAmount = accumulatedAmount.add(discountAmount);
        return validateMaxDiscountAmount(discountAmount);
    }

    private long validateMaxDiscountAmount(final BigDecimal discountAmount) {
        BigDecimal beforeAccumulatedAmount = accumulatedAmount.subtract(discountAmount);
        if (isOverMax(beforeAccumulatedAmount, discountAmount)) {
            return 0L;
        }
        if (accumulatedAmount.compareTo(MAX_MEMBERSHIP_AMOUNT) > 0) {
            return MAX_MEMBERSHIP_AMOUNT.subtract(beforeAccumulatedAmount).longValue();
        }
        return discountAmount.longValue();
    }

    private boolean isOverMax(final BigDecimal beforeAccumulatedAmount, final BigDecimal discountAmount) {
        if (beforeAccumulatedAmount.compareTo(MAX_MEMBERSHIP_AMOUNT) >= 0) {
            return true;
        }
        return discountAmount.compareTo(MAX_MEMBERSHIP_AMOUNT) > 0;
    }

}
