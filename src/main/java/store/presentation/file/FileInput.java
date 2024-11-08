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

    public List<String> readProductFileLines() {
        String productFileName = getFilePath(PRODUCT_FILE);
        try (Stream<String> lines = Files.lines(Paths.get(productFileName), StandardCharsets.UTF_8)) {
            return lines.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

}
