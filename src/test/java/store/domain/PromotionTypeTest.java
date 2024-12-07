package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.domain.PromotionType.ONE_PLUS_ONE;
import static store.domain.PromotionType.TWO_PLUS_ONE;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionTypeTest {

    @DisplayName("프로모션 이름으로 해당되는 프로모션 타입을 조회한다.")
    @MethodSource("providePromotionNameAndType")
    @ParameterizedTest
    void 프로모션_타입_이름_조회_성공(PromotionType expectedType, String promotionName) {
        assertThat(PromotionType.findByName(promotionName)).isEqualTo(expectedType);
    }

    @DisplayName("프로모션 이름으로 해당되는 프로모션 타입이 없다면 예외가 발생한다.")
    @Test
    void 프로모션_타입_이름_조회_실패() {
        assertThatThrownBy(() -> PromotionType.findByName("음료2+1"))
                .isInstanceOf(NoSuchElementException.class);
    }

    private static Stream<Arguments> providePromotionNameAndType() {
        return Stream.of(
                Arguments.of(TWO_PLUS_ONE, "탄산2+1"),
                Arguments.of(ONE_PLUS_ONE, "MD추천상품"),
                Arguments.of(ONE_PLUS_ONE, "반짝할인")
        );
    }

}
