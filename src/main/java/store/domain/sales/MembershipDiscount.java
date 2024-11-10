package store.domain.sales;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MembershipDiscount {

    private static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(30L);
    private static final BigDecimal MAX_MEMBERSHIP_AMOUNT = BigDecimal.valueOf(8000L);

    private BigDecimal accumulatedAmount;

    public long calculateDiscountAmount(final long amount) {
        BigDecimal discountAmount = DISCOUNT_RATE.multiply(BigDecimal.valueOf(amount))
                .divide(BigDecimal.valueOf(100L), MathContext.DECIMAL32)
                .setScale(0, RoundingMode.HALF_UP);
        accumulatedAmount = accumulatedAmount.add(discountAmount);
        return discountAmount.longValue();
    }
}
