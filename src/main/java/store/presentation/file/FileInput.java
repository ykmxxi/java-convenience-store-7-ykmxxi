package store.presentation.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class FileInput {

    private static final String ROOT_PATH = "src/main/resources/";
    private static final String PROPERTY_FILE = "application.properties";
    private static final String PRODUCT_FILE = "product.file";
    private static final String PROMOTION_FILE = "promotion.file";

    private final Map<String, Integer> productColumnIndex = new HashMap<>();
    private final Map<String, Integer> promotionColumnIndex = new HashMap<>();

    public List<List<String>> readProductTuples() {
        return readTuples(getFilePath(PRODUCT_FILE), productColumnIndex);
    }

    public List<List<String>> readPromotionTuples() {
        return readTuples(getFilePath(PROMOTION_FILE), promotionColumnIndex);
    }

    private List<List<String>> readTuples(final String filePath, final Map<String, Integer> columnIndex) {
        List<String> fileLines = readFileLines(filePath);
        saveColumnIndex(fileLines.getFirst(), columnIndex);
        return splitTupleColumns(fileLines, columnIndex.size());
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

    private void saveColumnIndex(final String columnNamesLine, final Map<String, Integer> columnIndex) {
        List<String> columnNames = splitByComma(columnNamesLine);
        for (int index = 0; index < columnNames.size(); index++) {
            columnIndex.put(columnNames.get(index), index);
        }
    }

    private List<List<String>> splitTupleColumns(final List<String> fileLines, final int countOfColumn) {
        return fileLines.stream()
                .skip(1)
                .map(fileLine -> splitTupleColumn(fileLine, countOfColumn))
                .toList();
    }

    private List<String> splitTupleColumn(final String fileLine, final int countOfColumn) {
        List<String> tuple = splitByComma(fileLine);
        validateTupleColumnCount(tuple.size(), countOfColumn);
        return tuple;
    }

    private List<String> splitByComma(final String fileLine) {
        return Arrays.stream(fileLine.split(","))
                .toList();
    }

    private void validateTupleColumnCount(final int countOfTupleColumn, final int countOfColumn) {
        if (countOfTupleColumn != countOfColumn) {
            throw new IllegalStateException("데이터 파일에 누락된 정보가 존재합니다.");
        }
    }

    public int getProductColumnIndexOf(final String columnName) {
        return productColumnIndex.get(columnName);
    }

}
