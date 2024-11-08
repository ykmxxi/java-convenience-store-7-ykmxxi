package store.repository.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import store.domain.inventory.Name;
import store.domain.inventory.Product;

class ProductRepositoryTest {

    private Product product;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        product = Product.storage("콜라", 1_000L);
        productRepository = new ProductRepository();
    }

    @Test
    void 상품_조회() {
        List<Product> products = productRepository.saveAll(List.of(product));

        Product findProduct = productRepository.find(Name.from("콜라"));

        assertThat(findProduct).isEqualTo(product);
    }

    @Test
    void 상품_조회_실패() {
        List<Product> products = productRepository.saveAll(List.of(product));

        assertThatThrownBy(() -> productRepository.find(Name.from("사이다")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_모두_저장() {
        List<Product> products = productRepository.saveAll(List.of(product));

        assertThat(products.getFirst()).extracting("price")
                .extracting("value")
                .isEqualTo(1_000L);
    }

}
