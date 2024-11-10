package store.service.inventory;

import java.util.ArrayList;
import java.util.List;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Promotion;
import store.domain.inventory.PromotionProduct;
import store.domain.inventory.PromotionType;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;
import store.repository.Repository;
import store.service.inventory.dto.ProductResponse;

public class InventoryService {

    private final Repository<Product, Name> productRepository;
    private final Repository<ProductStock, Product> productStockRepository;
    private final Repository<Promotion, PromotionType> promotionRepository;
    private final Repository<PromotionProduct, Product> promotionProductsRepository;
    private final StorageRequestMapper storageRequestMapper;

    public InventoryService(final Repository<Product, Name> productRepository,
                            final Repository<ProductStock, Product> productStockRepository,
                            final Repository<Promotion, PromotionType> promotionRepository,
                            final Repository<PromotionProduct, Product> promotionProductsRepository
    ) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
        this.promotionRepository = promotionRepository;
        this.promotionProductsRepository = promotionProductsRepository;
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

    public void savePromotionProducts(final List<PromotionProductStorageRequest> productStorageRequests) {
        Iterable<PromotionProduct> promotionProducts = storageRequestMapper.toPromotionProducts(
                productStorageRequests, promotionRepository, productRepository
        );
        promotionProductsRepository.saveAll(promotionProducts);
    }

    public List<ProductResponse> getProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductStock productStock = productStockRepository.find(product);
            if (isPromotionProduct(product)) {
                PromotionProduct promotionProduct = promotionProductsRepository.find(product);
                productResponses.add(new ProductResponse(product.name().value(), product.price(),
                        productStock.promotionQuantity(),
                        promotionProduct.promotion().name()));
            }
            productResponses.add(new ProductResponse(product.name().value(), product.price(),
                    productStock.normalQuantity(), ""));
        }
        return productResponses;
    }

    private boolean isPromotionProduct(final Product product) {
        return promotionProductsRepository.exists(product);
    }

}
