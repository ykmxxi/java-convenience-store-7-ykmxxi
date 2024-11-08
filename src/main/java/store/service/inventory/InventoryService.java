package store.service.inventory;

import java.util.List;

import store.domain.inventory.Product;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.repository.inventory.ProductRepository;

public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProducts(final List<ProductStorageRequest> productStorageRequests) {
        List<Product> products = productRepository.saveAll(toProducts(productStorageRequests));
    }

    private List<Product> toProducts(final List<ProductStorageRequest> productStorageRequests) {
        return productStorageRequests.stream()
                .map(request -> Product.storage(
                        request.name(), request.price()
                ))
                .toList();
    }

}
