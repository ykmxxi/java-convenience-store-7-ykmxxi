package store.service.inventory;

import java.util.List;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Promotion;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;
import store.repository.Repository;

public class InventoryService {

    private final Repository<Product, Name> productRepository;
    private final Repository<ProductStock, Product> productStockRepository;
    private final Repository<Promotion, Name> promotionRepository;
    private final StorageRequestMapper storageRequestMapper;

    public InventoryService(
            final Repository<Product, Name> productRepository,
            final Repository<ProductStock, Product> productStockRepository,
            final Repository<Promotion, Name> promotionRepository
    ) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
        this.promotionRepository = promotionRepository;
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

    public void savePromotions(final List<PromotionStorageRequest> promotionStorageRequests) {
        Iterable<Promotion> promotions = storageRequestMapper.toPromotions(promotionStorageRequests);
        promotionRepository.saveAll(promotions);
    }

}
