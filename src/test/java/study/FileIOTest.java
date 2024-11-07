package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileIOTest {

    private static final String ROOT_PATH = "src/test/java/study/resources/";

    @DisplayName(".properties 파일에 저장된 값을 읽어오기")
    @Test
    void 프로퍼티_읽어오기() throws IOException {
        String fileName = readProperties();

        assertThat(fileName).isEqualTo("src/test/java/study/resources/testdata.md");
    }

    @DisplayName(".md 파일 모든 줄 읽어오기")
    @Test
    void md_파일_읽어오기() throws IOException {
        String dataUri = readProperties();

        try (Stream<String> lines = Files.lines(Paths.get(dataUri), StandardCharsets.UTF_8)) {
            List<String> list = lines.toList();

            assertThat(list.getFirst()).isEqualTo("name,age,birthday,phone");
            assertThat(list.get(1)).isEqualTo("홍길동,20,2004-01-01,010-1234-4321");
            assertThat(list.getLast()).isEqualTo("이순신,25,1999-12-31,null");
        }

    }

    private String readProperties() throws IOException {
        String fileName;
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ROOT_PATH + "test.properties")) {
            properties.load(fileInputStream);
            fileName = (String) properties.get("datafilename");
        }
        return ROOT_PATH + fileName;
    }

}
