package store.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {


    @DisplayName("상품 가격이 1원 미만인 경우 상품 파일에서 잘못 등록한 상품 가격으로 발생한 시스템 예외다.")
    @ValueSource(longs = {0L, -1L})
    @ParameterizedTest
    void 가격이_1원_미만인_경우_시스템_예외(long value) {
        assertThatThrownBy(() -> Price.from(value))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("프로그램을 다시 실행해 주세요.");
    }

}
