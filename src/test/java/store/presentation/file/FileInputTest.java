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
        List<String> productFileLines = fileInput.readProducts();

        assertThat(productFileLines).contains(
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1",
                "사이다,1000,7,null",
                "오렌지주스,1800,9,MD추천상품",
                "탄산수,1200,5,탄산2+1",
                "물,500,10,null",
                "비타민워터,1500,6,null",
                "감자칩,1500,5,반짝할인",
                "감자칩,1500,5,null",
                "초코바,1200,5,MD추천상품",
                "초코바,1200,5,null",
                "에너지바,2000,5,null",
                "정식도시락,6400,8,null",
                "컵라면,1700,1,MD추천상품",
                "컵라면,1700,10,null"
        );
    }

    @DisplayName("행사 파일의 모든 라인을 한 줄씩 읽어온다.")
    @Test
    void 행사_파일_읽어오기_성공() {
        List<String> productFileLines = fileInput.readPromotions();

        assertThat(productFileLines).contains(
                "name,buy,get,start_date,end_date",
                "탄산2+1,2,1,2024-01-01,2024-12-31",
                "MD추천상품,1,1,2024-01-01,2024-12-31",
                "반짝할인,1,1,2024-11-01,2024-11-30"
        );
    }

}
