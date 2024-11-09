package store.service.inventory;

import java.util.List;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.repository.Repository;

public class InventoryService {

    private final Repository<Product, Name> productRepository;
    private final Repository<ProductStock, Product> productStockRepository;
    private final StorageRequestMapper storageRequestMapper;

    public InventoryService(
            final Repository<Product, Name> productRepository,
            final Repository<ProductStock, Product> productStockRepository
    ) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
        this.storageRequestMapper = new StorageRequestMapper();
    }

    public void saveProducts(final List<ProductStorageRequest> productStorageRequests) {
        Iterable<Product> products = storageRequestMapper.toProducts(productStorageRequests);
        productRepository.saveAll(products);
    }

    public void saveProductStocks(final List<ProductStockStorageRequest> productStockStorageRequests) {
        Iterable<ProductStock> productStocks = storageRequestMapper.toProductStocks(
                productStockStorageRequests, productRepository);
        productStockRepository.saveAll(productStocks);
    }

}
