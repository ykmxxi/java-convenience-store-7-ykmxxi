package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductsTest {

    private Product product;
    private Products products;

    @BeforeEach
    void setUp() {
        product = Product.storage("콜라", 1_000L);
        products = new Products();
    }

    @DisplayName("상품들을 모두 저장하면 이름으로 찾아올 수 있다.")
    @Test
    void 상품들을_모두_저장() {
        products.saveAll(List.of(product));

        assertThat(products.find(Name.from("콜라"))).isEqualTo(product);
    }

    @DisplayName("존재하지 않는 이름의 상품을 조회하면 예외가 발생한다.")
    @Test
    void 상품_조회_실패() {
        products.saveAll(List.of(product));

        assertThatThrownBy(() -> products.find(Name.from("없음")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }

    @DisplayName("존재하는 모든 상품을 조회한다.")
    @Test
    void 모든_상품을_조회() {
        products.saveAll(List.of(product));

        assertThat(products.findAll()).contains(product);
    }

}
