package store.service.inventory;

import java.time.LocalDateTime;
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
        addProductResponse(productResponses);
        return productResponses;
    }

    private void addProductResponse(final List<ProductResponse> productResponses) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductStock productStock = productStockRepository.find(product);
            if (isPromotionProduct(product)) {
                addPromotionResponse(product, productResponses, productStock);
            }
            addNormalResponse(product, productResponses, productStock);
        }
    }

    private void addPromotionResponse(final Product product, final List<ProductResponse> productResponses,
                                      final ProductStock productStock
    ) {
        PromotionProduct promotionProduct = promotionProductsRepository.find(product);
        productResponses.add(new ProductResponse(product.name().value(), product.price(),
                productStock.promotionQuantity(), promotionProduct.promotion().name())
        );
    }

    private void addNormalResponse(final Product product, final List<ProductResponse> productResponses,
                                   final ProductStock productStock
    ) {
        productResponses.add(new ProductResponse(product.name().value(), product.price(),
                productStock.normalQuantity(), ""));
    }

    private boolean isPromotionProduct(final Product product) {
        return promotionProductsRepository.exists(product);
    }

    public Product findProduct(final String productName) {
        return productRepository.find(Name.from(productName));
    }

    public boolean canReceivePromotion(final Product product, final LocalDateTime createdAt) {
        PromotionProduct promotionProduct = promotionProductsRepository.find(product);
        Promotion promotion = promotionRepository.find(promotionProduct.promotion().promotionType());
        return isPromotionProduct(product) && promotion.isPromotionPeriod(createdAt);
    }

    public ProductStock findProductStock(final Product product) {
        return productStockRepository.find(product);
    }

    public int calculatePromotionCount(final Product product, final int orderQuantity) {
        ProductStock productStock = productStockRepository.find(product);
        PromotionProduct promotionProduct = promotionProductsRepository.find(product);
        Promotion promotion = promotionRepository.find(promotionProduct.promotion().promotionType());
        return promotion.calculatePromotionCount(productStock.promotionQuantity(), orderQuantity);
    }

    public PromotionProduct findPromotionProduct(final Product product) {
        return promotionProductsRepository.find(product);
    }

    public int calculatePromotionProductCount(final Product product, final int promotionCount) {
        PromotionProduct promotionProduct = promotionProductsRepository.find(product);
        return promotionProduct.calculatePromotionProductCount(promotionCount);
    }

}
