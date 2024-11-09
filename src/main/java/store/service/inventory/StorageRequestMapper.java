package store.service.inventory;

import java.util.List;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Stock;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
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

}
