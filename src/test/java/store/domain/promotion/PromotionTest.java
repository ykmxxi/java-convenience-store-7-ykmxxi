package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @DisplayName("프로모션 이름, Buy N for Get 1 수량, 기간이 모두 같으면 생성 성공한다.")
    @Test
    void 프로모션_타입_생성_성공() {
        assertDoesNotThrow(() -> Promotion.of(name, 2, 1, startDate, endDate));
    }

}
