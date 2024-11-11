package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionTest {

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        name = "탄산2+1";
        startDate = LocalDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.MIDNIGHT);
        endDate = LocalDateTime.of(LocalDate.parse("2024-12-31"), LocalTime.MAX);
        promotion = Promotion.of(name, 2, 1, startDate, endDate);
    }

    @DisplayName("프로모션 이름, Buy N for Get 1 수량, 기간이 모두 같으면 생성 성공한다.")
    @Test
    void 프로모션_타입_생성_성공() {
        assertDoesNotThrow(() -> Promotion.of(name, 2, 1, startDate, endDate));
    }

    @DisplayName("행사 기간이면 true를 반환한다.")
    @ValueSource(strings = {"2024-01-01T00:00:01", "2024-12-31T23:59:59"})
    @ParameterizedTest
    void 행사_기간이면_true_반환(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date);

        assertThat(promotion.isPromotionPeriod(localDateTime)).isTrue();
    }

    @DisplayName("행사 기간이 아니면 false를 반환한다.")
    @ValueSource(strings = {"2023-12-31T23:59:59", "2025-01-01T00:00:00"})
    @ParameterizedTest
    void 행사_기간이_아니면_false_반환(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date);

        assertThat(promotion.isPromotionPeriod(localDateTime)).isFalse();
    }

}
