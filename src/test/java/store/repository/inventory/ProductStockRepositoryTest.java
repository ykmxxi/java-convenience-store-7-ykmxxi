package store.repository.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Stock;

class ProductStockRepositoryTest {

    private Product product;
    private ProductStock productStock;
    private ProductStockRepository productStockRepository;

    @BeforeEach
    void setUp() {
        product = Product.storage("콜라", 1_000L);
        productStock = productStock();
        productStockRepository = new ProductStockRepository();
    }

    @Test
    void 상품_재고_조회() {
        List<ProductStock> productStocks = productStockRepository.saveAll(List.of(productStock));

        ProductStock findProductStock = productStockRepository.find(product);

        assertThat(findProductStock).extracting("stock")
                .extracting("normal", "promotion")
                .containsExactly(5, 10);
    }

    @Test
    void 상품_재고_조회_실패() {
        assertThatThrownBy(() -> productStockRepository.find(Product.storage("사이다", 1_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_재고를_모두_저장() {
        List<ProductStock> productStocks = productStockRepository.saveAll(List.of(productStock));

        assertThat(productStocks.getFirst()).extracting("stock")
                .extracting("normal", "promotion")
                .containsExactly(5, 10);
    }

    private ProductStock productStock() {
        return new ProductStock(product, new Stock(5, 10));
    }

}
