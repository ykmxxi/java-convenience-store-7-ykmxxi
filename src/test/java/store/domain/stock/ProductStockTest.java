package store.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.domain.product.Product;

class ProductStockTest {

    private Product product;
    private ProductStock productStock;

    @BeforeEach
    void setUp() {
        product = Product.storage("콜라", 1_000L);
        productStock = new ProductStock(product, new Stock(10, 10));
    }

    @DisplayName("상품 재고 수량이 주문 수량 이상인 경우 true 반환한다.")
    @Test
    void 상품_재고_수량이_주문_수량_이상인_경우() {
        assertThat(productStock.isEnough(20)).isTrue();
    }

    @DisplayName("상품 재고 수량이 주문 수량 미만인 경우 false 반환한다.")
    @Test
    void 상품_재고_수량이_주문_수량_미만인_경우() {
        assertThat(productStock.isEnough(21)).isFalse();
    }

}
