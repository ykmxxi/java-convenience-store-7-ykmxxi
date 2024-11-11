package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        name = "탄산2+1";
        startDate = LocalDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.MIDNIGHT);
        endDate = LocalDateTime.of(LocalDate.parse("2024-12-31"), LocalTime.MAX);
    }

    @DisplayName("프로모션 이름이 다르면 생성에 실패하고 시스템 예외가 발생한다.")
    @Test
    void 프로모션_이름이_다른_경우_생성_실패() {
        assertThatThrownBy(() -> Promotion.of("음료2+1", 2, 1, startDate, endDate))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("프로모션 Buy N for Get 1 수량이 다르면 생성 실패하고 시스템 예외가 발생한다.")
    @Test
    void 프로모션_수량이_다른_경우_생성_실패() {
        assertThatThrownBy(() -> Promotion.of(name, 1, 1, startDate, endDate))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("프로모션 기간이 다르면 생성 실패하고 시스템 예외가 발생한다.")
    @CsvSource(value = {"2024-01-01T00:00:00,2025-01-01T00:00:00", "2024-01-01T00:00:01, 2024-12-31T23:59:59"})
    @ParameterizedTest
    void 프로모션_기간이_다른_경우_생성_실패(LocalDateTime startDate, LocalDateTime endDate) {
        assertThatThrownBy(() -> Promotion.of(name, 2, 1, startDate, endDate))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("프로모션 이름, Buy N for Get 1 수량, 기간이 모두 같으면 생성 성공한다.")
    @Test
    void 프로모션_타입_생성_성공() {
        assertDoesNotThrow(() -> Promotion.of(name, 2, 1, startDate, endDate));
    }

}
