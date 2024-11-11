package store.presentation.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileInput {

    private static final String ROOT_PATH = "src/main/resources/";
    private static final String PRODUCT_FILE = "products.md";
    private static final String PROMOTION_FILE = "promotions.md";
    private static final int COLUMN_NAME_LINE = 1;

    public List<List<String>> readProductTuples() {
        List<List<String>> productTuples = readTuples(ROOT_PATH + PRODUCT_FILE);
        return productTuples.stream()
                .skip(COLUMN_NAME_LINE)
                .toList();
    }

    public List<List<String>> readPromotionTuples() {
        List<List<String>> promotionTuples = readTuples(ROOT_PATH + PROMOTION_FILE);
        return promotionTuples.stream()
                .skip(COLUMN_NAME_LINE)
                .toList();
    }

    private List<List<String>> readTuples(final String filePath) {
        List<String> fileLines = readFileLines(filePath);
        return toTuples(fileLines);
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
