package store.presentation.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileInputTest {

    private FileInput fileInput;

    @BeforeEach
    void setUp() {
        fileInput = new FileInput();
    }

    @DisplayName("상품 파일의 모든 라인을 한 줄씩 읽어온다.")
    @Test
    void 상품_파일_읽어오기_성공() {
        List<List<String>> productTuples = fileInput.readProductTuples();

        assertThat(productTuples).contains(
                List.of("콜라", "1000", "10", "탄산2+1"),
                List.of("콜라", "1000", "10", "null"),
                List.of("사이다", "1000", "8", "탄산2+1"),
                List.of("사이다", "1000", "7", "null"),
                List.of("오렌지주스", "1800", "9", "MD추천상품"),
                List.of("탄산수", "1200", "5", "탄산2+1"),
                List.of("물", "500", "10", "null"),
                List.of("비타민워터", "1500", "6", "null"),
                List.of("감자칩", "1500", "5", "반짝할인"),
                List.of("감자칩", "1500", "5", "null"),
                List.of("초코바", "1200", "5", "MD추천상품"),
                List.of("초코바", "1200", "5", "null"),
                List.of("에너지바", "2000", "5", "null"),
                List.of("정식도시락", "6400", "8", "null"),
                List.of("컵라면", "1700", "1", "MD추천상품"),
                List.of("컵라면", "1700", "10", "null")
        );
    }

    @DisplayName("행사 파일의 모든 라인을 한 줄씩 읽어온다.")
    @Test
    void 행사_파일_읽어오기_성공() {
        List<List<String>> promotionTuples = fileInput.readPromotionTuples();

        assertThat(promotionTuples).contains(
                List.of("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"),
                List.of("MD추천상품", "1", "1", "2024-01-01", "2024-12-31"),
                List.of("반짝할인", "1", "1", "2024-11-01", "2024-11-30")
        );
    }

}
