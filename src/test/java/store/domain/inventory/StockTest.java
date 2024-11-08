package store.domain.inventory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @DisplayName("일반 재고 수량이 음수인 경우 시스템 예외가 발생한다.")
    @Test
    void 일반_재고_수량이_음수인_경우_시스템_예외() {
        assertThatThrownBy(() -> new Stock(-1, 0))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("행사 재고 수량이 음수인 경우 시스템 예외가 발생한다.")
    @Test
    void 행사_재고_수량이_음수인_경우_시스템_예외() {
        assertThatThrownBy(() -> new Stock(0, -1))
                .isInstanceOf(IllegalStateException.class);
    }

}
