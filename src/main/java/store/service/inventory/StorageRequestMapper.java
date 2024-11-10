package store.service.inventory;

import java.util.List;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Promotion;
import store.domain.inventory.PromotionProduct;
import store.domain.inventory.PromotionType;
import store.domain.inventory.Stock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductStorageRequest;
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

    public Iterable<PromotionProduct> toPromotionProducts(
            final List<PromotionProductStorageRequest> productStorageRequests,
            final Repository<Promotion, PromotionType> promotionRepository,
            final Repository<Product, Name> productRepository
    ) {
        return productStorageRequests.stream()
                .map(request -> toPromotionProduct(request.name(), request.promotion(),
                        productRepository, promotionRepository)
                ).toList();
    }

    private PromotionProduct toPromotionProduct(final String productName, final String promotionName,
                                                final Repository<Product, Name> productRepository,
                                                final Repository<Promotion, PromotionType> promotionRepository
    ) {
        Product product = productRepository.find(Name.from(productName));
        Promotion promotion = promotionRepository.find(PromotionType.from(promotionName));
        return new PromotionProduct(product, promotion);
    }

}
