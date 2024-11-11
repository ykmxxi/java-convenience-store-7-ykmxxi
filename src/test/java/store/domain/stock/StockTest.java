package store.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock(10, 10);
    }

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

    @DisplayName("해당 상품의 주문 수량을 받았을 때 재고가 충분하면 true 반환한다.")
    @Test
    void 총_재고가_충분한_경우() {
        assertThat(stock.isEnough(20)).isTrue();
    }

    @DisplayName("해당 상품의 주문 수량을 받았을 때 재고가 부족하면 false 반환한다.")
    @Test
    void 총_재고가_부족한_경우() {
        assertThat(stock.isEnough(21)).isFalse();
    }

}
