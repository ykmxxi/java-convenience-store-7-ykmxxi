package store.service.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.repository.Repository;

class StorageRequestMapperTest {

    private StorageRequestMapper storageRequestMapper;

    @BeforeEach
    void setUp() {
        storageRequestMapper = new StorageRequestMapper();
    }

    @DisplayName("상품 저장 요청을 상품 도메인 타입으로 변환한다.")
    @Test
    void 상품_저장_요청을_상품_도메인으로_변환() {
        List<ProductStorageRequest> requests = List.of(new ProductStorageRequest("콜라", 1_000L));

        assertThat(storageRequestMapper.toProducts(requests))
                .hasOnlyElementsOfType(Product.class);
    }

    @DisplayName("상품 재고 저장 요청을 상품 재고 도메인 타입으로 변환한다.")
    @Test
    void 상품_재고_저장_요청을_상품_도메인으로_변환() {
        List<ProductStockStorageRequest> requests = List.of(new ProductStockStorageRequest("콜라", 10, 10));

        assertThat(storageRequestMapper.toProductStocks(requests, new ProductRepositoryStub()))
                .hasOnlyElementsOfType(ProductStock.class);
    }

    static class ProductRepositoryStub implements Repository<Product, Name> {

        @Override
        public Product find(final Name name) {
            return Product.storage("콜라", 1_000L);
        }

        @Override
        public List<Product> saveAll(final Iterable<Product> entities) {
            return List.of(Product.storage("콜라", 1_000L));
        }

    }

}