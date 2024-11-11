package store.domain.sales;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MembershipDiscountTest {

    @DisplayName("멤버십 최대 한도 금액을 초과하면 0원을 반환한다.")
    @Test
    void 멤버십_최대_한도를_초과하면_0원() {
        MembershipDiscount membershipDiscount = new MembershipDiscount();

        assertThat(membershipDiscount.calculateDiscountAmount(27_000L)).isEqualTo(0L);
    }

    @DisplayName("멤버십 최대 한도 금액을 초과하면 0원을 반환한다.")
    @Test
    void 최대_한도_이하는_할인_금액을_모두_돌려준다() {
        MembershipDiscount membershipDiscount = new MembershipDiscount();

        assertThat(membershipDiscount.calculateDiscountAmount(10_000L)).isEqualTo(3_000L);
    }

    @DisplayName("누적 멤버십 할인 금액이 한도 금액을 초과하면 그 값만큼 빼서 반환한다.")
    @Test
    void 누적_멤버십_최대_한도_초과() {
        MembershipDiscount membershipDiscount = new MembershipDiscount();

        long first = membershipDiscount.calculateDiscountAmount(10_000L);
        long second = membershipDiscount.calculateDiscountAmount(10_000L);
        long third = membershipDiscount.calculateDiscountAmount(10_000L);

        assertThat(first).isEqualTo(3_000L);
        assertThat(second).isEqualTo(3_000L);
        assertThat(third).isEqualTo(2_000L);
    }

}
