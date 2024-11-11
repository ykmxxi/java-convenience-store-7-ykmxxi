package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionTypeTest {

    @DisplayName("프로모션 타입과 같은 이름을 넣으면 해당 타입을 반환한다.")
    @MethodSource("provideNameAndPromotionType")
    @ParameterizedTest
    void 이름에_맞는_프로모션_타입_반환(String name, PromotionType promotionType) {
        assertThat(PromotionType.from(name)).isEqualTo(promotionType);
    }

    @DisplayName("프로모션 타입과 이름이 다르면 생성에 실패하고 시스템 예외가 발생한다.")
    @Test
    void 프로모션_타입_이름이_다른_경우_생성_실패() {
        assertThatThrownBy(() -> PromotionType.from("음료2+1"))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("프로모션 타입과 Buy N for Get 1 수량이 다르면 생성 실패하고 시스템 예외가 발생한다.")
    @Test
    void 프로모션_타입_수량이_다른_경우_생성_실패() {
        PromotionType promotionType = PromotionType.from("탄산2+1");
        assertThatThrownBy(() -> promotionType.validateBuyNGetOneFree(1, 1))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("프로모션 타입과 기간이 다르면 생성 실패하고 시스템 예외가 발생한다.")
    @CsvSource(value = {"2024-01-01T00:00:00,2025-01-01T00:00:00", "2024-01-01T00:00:01, 2024-12-31T23:59:59"})
    @ParameterizedTest
    void 프로모션_타입_기간이_다른_경우_생성_실패(LocalDateTime startDate, LocalDateTime endDate) {
        PromotionType promotionType = PromotionType.from("탄산2+1");
        assertThatThrownBy(() -> promotionType.validatePeriod(startDate, endDate))
                .isInstanceOf(IllegalStateException.class);
    }

    static Stream<Arguments> provideNameAndPromotionType() {
        return Stream.of(
                Arguments.of("탄산2+1", PromotionType.CARBONATED_TWO_PLUS_ONE),
                Arguments.of("MD추천상품", PromotionType.MD_RECOMMENDED_PRODUCT),
                Arguments.of("반짝할인", PromotionType.FLASH_DISCOUNT)
        );
    }

}
