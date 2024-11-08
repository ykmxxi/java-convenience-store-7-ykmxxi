package store.presentation.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class FileInput {

    private static final String ROOT_PATH = "src/main/resources/";
    private static final String PROPERTY_FILE = "application.properties";
    private static final String PRODUCT_FILE = "product.file";
    private static final String PROMOTION_FILE = "promotion.file";

    public List<String> readProducts() {
        return readFileLines(getFilePath(PRODUCT_FILE));
    }

    public List<String> readPromotions() {
        return readFileLines(getFilePath(PROMOTION_FILE));
    }

    private String getFilePath(final String propertyKey) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ROOT_PATH + PROPERTY_FILE)) {
            properties.load(fileInputStream);
            return ROOT_PATH + properties.get(propertyKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readFileLines(final String promotionFilePath) {
        try (Stream<String> lines = Files.lines(Paths.get(promotionFilePath), StandardCharsets.UTF_8)) {
            return lines.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
