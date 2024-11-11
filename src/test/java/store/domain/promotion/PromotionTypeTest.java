package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionTypeTest {

    @DisplayName("프로모션 타입과 같은 이름을 넣으면 해당 타입을 반환한다.")
    @MethodSource("provideNameAndPromotionType")
    @ParameterizedTest
    void 이름에_맞는_프로모션_타입_반환(String name, PromotionType promotionType) {
        assertThat(PromotionType.from(name)).isEqualTo(promotionType);
    }

    static Stream<Arguments> provideNameAndPromotionType() {
        return Stream.of(
                Arguments.of("탄산2+1", PromotionType.CARBONATED_TWO_PLUS_ONE),
                Arguments.of("MD추천상품", PromotionType.MD_RECOMMENDED_PRODUCT),
                Arguments.of("반짝할인", PromotionType.FLASH_DISCOUNT)
        );
    }

}
