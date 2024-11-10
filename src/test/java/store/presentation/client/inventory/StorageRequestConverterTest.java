package store.presentation.client.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;

class StorageRequestConverterTest {

    private StorageRequestConverter converter;
    private List<List<String>> productTuples;
    private List<List<String>> promotionTuples;

    @BeforeEach
    void setUp() {
        converter = new StorageRequestConverter();
        productTuples = List.of(List.of("콜라", "1000", "10", "탄산2+1"), List.of("콜라", "1000", "10", "null"));
        promotionTuples = List.of(List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"));
    }

    @DisplayName("상품 이름별로 1개의 상품 저장 요청을 생성한다.")
    @Test
    void 상품_저장_요청() {
        assertThat(converter.toProductStorageRequests(productTuples))
                .containsExactly(new ProductStorageRequest("콜라", 1000L));
    }

    @DisplayName("상품 이름별로 일반 재고와 행사 재고 저장 요청을 생성한다.")
    @Test
    void toProductStockStorageRequests() {
        assertThat(converter.toProductStockStorageRequests(productTuples))
                .containsExactly(new ProductStockStorageRequest("콜라", 10, 10));
    }

    @DisplayName("행사 이름별로 이름, Buy N for Get 1, 시작 날짜, 끝 날짜 저장 요청을 생성한다.")
    @Test
    void toPromotionStorageRequests() {
        assertThat(converter.toPromotionStorageRequests(promotionTuples))
                .containsExactly(new PromotionStorageRequest("탄산2+1", 2, 1,
                        LocalDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.MIDNIGHT),
                        LocalDateTime.of(LocalDate.parse("2024-12-31"), LocalTime.MAX))
                );
    }

    @DisplayName("행사 이름별로 행사 상품 목록 저장 요청을 생성한다.")
    @Test
    void 할인_상품_저장_요청_변환() {
        assertThat(converter.toPromotionProductStorageRequests(productTuples))
                .containsExactly(new PromotionProductStorageRequest("콜라", "탄산2+1"));
    }

}
