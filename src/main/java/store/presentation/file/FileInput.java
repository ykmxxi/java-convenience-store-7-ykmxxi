package store.presentation.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class FileInput {

    private static final String ROOT_PATH = "src/main/resources/";
    private static final String PROPERTY_FILE = "application.properties";
    private static final String PRODUCT_FILE = "product.file";
    private static final String PROMOTION_FILE = "promotion.file";
    private static final int COLUMN_NAME_LINE = 1;

    public List<List<String>> readProductTuples() {
        List<List<String>> productTuples = readTuples(getFilePath(PRODUCT_FILE));
        return productTuples.stream()
                .skip(COLUMN_NAME_LINE)
                .toList();
    }

    public List<List<String>> readPromotionTuples() {
        List<List<String>> promotionTuples = readTuples(getFilePath(PROMOTION_FILE));
        return promotionTuples.stream()
                .skip(COLUMN_NAME_LINE)
                .toList();
    }

    private List<List<String>> readTuples(final String filePath) {
        List<String> fileLines = readFileLines(filePath);
        return toTuples(fileLines);
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

    private List<List<String>> toTuples(final List<String> fileLines) {
        return fileLines.stream()
                .map(this::splitTupleColumn)
                .toList();
    }

    private List<String> splitTupleColumn(final String fileLine) {
        return Arrays.stream(fileLine.split(","))
                .toList();
    }

}
