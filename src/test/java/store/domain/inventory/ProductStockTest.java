package store.domain.inventory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductStockTest {

    @DisplayName("상품 재고 수량 중 음수가 존재하면 시스템 예외가 발생한다.")
    @CsvSource(value = {"-1,0", "0,-1", "-1,-1"})
    @ParameterizedTest
    void 상품_재고_수량에_음수가_존재하는_경우_시스템_예외(int normal, int promotion) {
        Product product = Product.storage("콜라", 1_000L);

        assertThatThrownBy(() -> new ProductStock(product, new Stock(normal, promotion)))
                .isInstanceOf(IllegalStateException.class);
    }

}
