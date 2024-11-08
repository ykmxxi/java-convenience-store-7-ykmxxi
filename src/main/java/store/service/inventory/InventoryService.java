package store.service.inventory;

import java.util.List;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Stock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.repository.inventory.ProductRepository;
import store.repository.inventory.ProductStockRepository;

public class InventoryService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    public InventoryService(
            final ProductRepository productRepository,
            final ProductStockRepository productStockRepository
    ) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
    }

    public void saveProducts(final List<ProductStorageRequest> productStorageRequests) {
        List<Product> products = productRepository.saveAll(toProducts(productStorageRequests));
    }

    private Iterable<Product> toProducts(final List<ProductStorageRequest> productStorageRequests) {
        return productStorageRequests.stream()
                .map(request -> Product.storage(
                        request.name(), request.price()
                ))
                .toList();
    }

    public void saveProductStocks(final List<ProductStockStorageRequest> productStockStorageRequests) {
        productStockRepository.saveAll(toProductStocks(productStockStorageRequests));
    }

    private Iterable<ProductStock> toProductStocks(final List<ProductStockStorageRequest> productStockStorageRequests) {
        return productStockStorageRequests.stream()
                .map(this::toProductStock)
                .toList();
    }

    private ProductStock toProductStock(final ProductStockStorageRequest request) {
        Product product = productRepository.find(Name.from(request.name()));
        Stock stock = new Stock(request.normalQuantity(), request.promotionQuantity());
        return new ProductStock(product, stock);
    }

}
