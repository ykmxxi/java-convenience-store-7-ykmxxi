package store.domain.inventory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @DisplayName("상품 이름이 null, 공백(빈 문자열)인 경우는 상품 파일을 읽어오는 과정에서 발생한 시스템 예외다.")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    @ParameterizedTest
    void 이름이_null인_경우_시스템_예외(String value) {
        assertThatThrownBy(() -> Name.from(value))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("프로그램을 다시 실행해 주세요.");
    }

}
