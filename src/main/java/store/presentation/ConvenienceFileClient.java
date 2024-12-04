package store.presentation;

import java.util.ArrayList;
import java.util.List;

import store.presentation.dto.ProductSaveRequest;
import store.service.ConvenienceFileService;

public class ConvenienceFileClient {

    private final ConvenienceFileReader convenienceFileReader;
    private final ConvenienceFileService convenienceFileService;

    public ConvenienceFileClient(final ConvenienceFileReader convenienceFileReader,
                                 final ConvenienceFileService convenienceFileService
    ) {
        this.convenienceFileReader = convenienceFileReader;
        this.convenienceFileService = convenienceFileService;
    }

    public void saveProducts() {
        List<String> productsLines = convenienceFileReader.readProductsFile()
                .stream()
                .skip(1L)
                .toList();
        List<ProductSaveRequest> productSaveRequests = new ArrayList<>();
        for (String productsLine : productsLines) {
            String[] tokens = productsLine.split(",");
            productSaveRequests.add(ProductSaveRequest.of(tokens[0],
                    Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]));
        }
        convenienceFileService.saveProducts(productSaveRequests);
    }

}
