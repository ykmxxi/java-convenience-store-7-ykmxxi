package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import store.domain.Product;
import store.presentation.dto.ProductSaveRequest;

class ConvenienceFileServiceTest {

    @Test
    void 상품_저장() {
        List<ProductSaveRequest> requests = List.of(ProductSaveRequest.of("콜라", 1000, 1, "탄산2+1"));
        ConvenienceFileService convenienceFileService = new ConvenienceFileService();

        convenienceFileService.saveProducts(requests);

        assertThat(convenienceFileService.getProducts()).contains(new Product("콜라", 1000));
    }

}
