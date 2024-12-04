package store.presentation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConvenienceFileReader {

    private static final String DIRECTORY = "src/main/resources/";
    private static final String PRODUCTS_FILE_NAME = "products.md";
    private static final String PROMOTIONS_FILE_NAME = "promotions.md";

    public List<String> readProductsFile() {
        try {
            return readAllFileLines(PRODUCTS_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readPromotionsFile() {
        try {
            return readAllFileLines(PROMOTIONS_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readAllFileLines(final String promotionsFileName) throws IOException {
        return Files.readAllLines(Path.of(DIRECTORY + promotionsFileName));
    }

}
