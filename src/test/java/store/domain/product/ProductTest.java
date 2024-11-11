package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @DisplayName("상품의 이름과 가격이 들어오면 상품 입고가 성공한다.")
    @Test
    void 상품_입고_성공() {
        Product product = Product.storage("콜라", 1_000L);

        assertThat(product.name()).isEqualTo(Name.from("콜라"));
        assertThat(product.price()).isEqualTo(1_000L);
    }

    @DisplayName("잘못된 이름과 가격이 들어오면 상품 입고가 실패한다.")
    @CsvSource(value = {" ,1000", "콜라,0"})
    @ParameterizedTest
    void 상품_입고_실패(String name, long price) {
        assertThatThrownBy(() -> Product.storage(name, price))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("프로그램을 다시 실행해 주세요.");
    }

}
