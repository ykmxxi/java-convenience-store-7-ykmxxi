package store.service.inventory;

import java.util.List;

import store.domain.product.Name;
import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionProduct;
import store.domain.promotion.PromotionType;
import store.domain.promotion.Promotions;
import store.domain.stock.ProductStock;
import store.domain.stock.Stock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;

public class StorageRequestMapper {

    public Iterable<Product> toProducts(final List<ProductStorageRequest> requests) {
        return requests.stream()
                .map(request -> Product.storage(
                        request.name(), request.price()
                ))
                .toList();
    }

    public Iterable<ProductStock> toProductStocks(final List<ProductStockStorageRequest> requests,
                                                  final Products products
    ) {
        return requests.stream()
                .map(request -> {
                    Product product = products.find(Name.from(request.name()));
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
            final Products products, final Promotions promotions
    ) {
        return productStorageRequests.stream()
                .map(request -> toPromotionProduct(request.name(), request.promotion(),
                        products, promotions)
                ).toList();
    }

    private PromotionProduct toPromotionProduct(final String productName, final String promotionName,
                                                final Products products, final Promotions promotions
    ) {
        Product product = products.find(Name.from(productName));
        Promotion promotion = promotions.find(PromotionType.from(promotionName));
        return new PromotionProduct(product, promotion);
    }

}
