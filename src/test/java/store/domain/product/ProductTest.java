package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품의 이름과 가격이 들어오면 상품 입고가 성공한다.")
    @Test
    void 상품_입고_성공() {
        Product product = Product.storage("콜라", 1_000L);

        assertThat(product.name()).isEqualTo(Name.from("콜라"));
        assertThat(product.price()).isEqualTo(1_000L);
    }

}
