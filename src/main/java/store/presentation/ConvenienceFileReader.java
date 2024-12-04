package store.presentation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConvenienceFileReader {

    private static final String DIRECTORY = "src/main/resources/";
    private static final String PRODUCTS_FILE_NAME = "products.md";

    public List<String> readProductsFile() {
        try {
            return Files.readAllLines(Path.of(DIRECTORY + PRODUCTS_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
