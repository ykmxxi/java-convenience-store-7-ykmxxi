package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import store.domain.Product;
import store.domain.Stock;
import store.presentation.dto.ProductSaveRequest;
import store.presentation.dto.PromotionSaveRequest;

class ConvenienceFileServiceTest {

    private ConvenienceFileService convenienceFileService;

    @BeforeEach
    void setUp() {
        convenienceFileService = new ConvenienceFileService();
    }

    @Test
    void 상품_저장() {
        List<ProductSaveRequest> requests = List.of(ProductSaveRequest.of("콜라", 1000, 1, "탄산2+1"));

        convenienceFileService.saveProducts(requests);

        assertThat(convenienceFileService.getProducts()).contains(new Product("콜라", 1000));
    }

    @Test
    void 프로모션_저장() {
        List<PromotionSaveRequest> requests = List.of(PromotionSaveRequest.of("탄산2+1", "2024-01-01", "2024-12-31"));

        convenienceFileService.savePromotions(requests);

        assertThat(convenienceFileService.findPromotionByName("탄산2+1"))
                .isNotNull();
    }

    @Test
    void 재고_저장() {
        List<ProductSaveRequest> requests = List.of(ProductSaveRequest.of("콜라", 1000, 1, "탄산2+1"));
        convenienceFileService.saveProducts(requests);

        convenienceFileService.saveProductsStock(requests);

        Stock stock = convenienceFileService.findProductStock("콜라");
        assertThat(stock.normalQuantity()).isEqualTo(0);
        assertThat(stock.promotionQuantity()).isEqualTo(1);
    }

}
