package store.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConvenienceFileReaderTest {

    @DisplayName("products.md 파일의 모든 라인을 읽어온다.")
    @Test
    void 상품_파일_모든_라인_읽어오기() {
        ConvenienceFileReader convenienceFileReader = new ConvenienceFileReader();

        List<String> productsLines = convenienceFileReader.readProductsFile();

        assertThat(productsLines).isNotEmpty();
    }

}
