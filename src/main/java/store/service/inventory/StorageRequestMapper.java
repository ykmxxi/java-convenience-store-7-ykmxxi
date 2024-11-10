package store.service.inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Promotion;
import store.domain.inventory.PromotionProducts;
import store.domain.inventory.PromotionType;
import store.domain.inventory.Stock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductsStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;
import store.repository.Repository;

public class StorageRequestMapper {

    public Iterable<Product> toProducts(final List<ProductStorageRequest> requests) {
        return requests.stream()
                .map(request -> Product.storage(
                        request.name(), request.price()
                ))
                .toList();
    }

    public Iterable<ProductStock> toProductStocks(final List<ProductStockStorageRequest> requests,
                                                  final Repository<Product, Name> productRepository
    ) {
        return requests.stream()
                .map(request -> {
                    Product product = productRepository.find(Name.from(request.name()));
                    return toProductStock(product, request);
                })
                .toList();
    }

    private ProductStock toProductStock(final Product product, final ProductStockStorageRequest request) {
        Stock stock = new Stock(request.normalQuantity(), request.promotionQuantity());
        return new ProductStock(product, stock);
    }

    public Iterable<Promotion> toPromotions(final List<PromotionStorageRequest> requests) {
        return requests.stream()
                .map(request -> Promotion.of(
                        request.name(), request.quantityOfBuy(), request.quantityOfFree(),
                        request.startDate(), request.endDate())
                ).toList();
    }

    public Iterable<PromotionProducts> toPromotionProducts(
            final List<PromotionProductsStorageRequest> productStorageRequests,
            final Repository<Promotion, PromotionType> promotionRepository,
            final Repository<Product, Name> productRepository
    ) {
        return savePromotionProducts(productStorageRequests,
                promotionRepository, productRepository).entrySet()
                .stream()
                .map(entry -> new PromotionProducts(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Map<Promotion, Set<Product>> savePromotionProducts(
            final List<PromotionProductsStorageRequest> productStorageRequests,
            final Repository<Promotion, PromotionType> promotionRepository,
            final Repository<Product, Name> productRepository) {
        Map<Promotion, Set<Product>> promotionProducts = new HashMap<>();
        productStorageRequests.forEach(request -> promotionProducts.computeIfAbsent(
                promotionRepository.find(PromotionType.from(request.promotion())),
                key -> new HashSet<>()).add(productRepository.find(Name.from(request.name()))));
        return promotionProducts;
    }

}
