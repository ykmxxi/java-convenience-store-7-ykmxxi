package store.service.inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import store.domain.product.Name;
import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionProduct;
import store.domain.promotion.PromotionProducts;
import store.domain.promotion.Promotions;
import store.domain.stock.ProductStock;
import store.domain.stock.ProductStorage;
import store.presentation.client.inventory.dto.ProductStockStorageRequest;
import store.presentation.client.inventory.dto.ProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionProductStorageRequest;
import store.presentation.client.inventory.dto.PromotionStorageRequest;
import store.service.inventory.dto.ProductResponse;

public class InventoryService {

    private final Products products;
    private final ProductStorage productStorage;
    private final Promotions promotions;
    private final PromotionProducts promotionProducts;
    private final StorageRequestMapper storageRequestMapper;

    public InventoryService() {
        this.products = new Products();
        this.productStorage = new ProductStorage();
        this.promotions = new Promotions();
        this.promotionProducts = new PromotionProducts();
        this.storageRequestMapper = new StorageRequestMapper();
    }

    public void saveProducts(final List<ProductStorageRequest> productStorageRequests) {
        products.saveAll(storageRequestMapper.toProducts(productStorageRequests));
    }

    public void saveProductStocks(final List<ProductStockStorageRequest> productStockStorageRequests) {
        productStorage.saveAll(storageRequestMapper.toProductStocks(productStockStorageRequests, products));
    }

    public void savePromotions(final List<PromotionStorageRequest> promotionStorageRequests) {
        promotions.saveAll(storageRequestMapper.toPromotions(promotionStorageRequests));
    }

    public void savePromotionProducts(final List<PromotionProductStorageRequest> productStorageRequests) {
        promotionProducts.saveAll(
                storageRequestMapper.toPromotionProducts(productStorageRequests, products, promotions));
    }

    public List<ProductResponse> getProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        addProductResponse(productResponses);
        return productResponses;
    }

    private void addProductResponse(final List<ProductResponse> productResponses) {
        for (Product product : products.findAll()) {
            ProductStock productStock = productStorage.find(product);
            if (promotionProducts.isPromotionProduct(product)) {
                addPromotionResponse(product, productResponses, productStock);
            }
            addNormalResponse(product, productResponses, productStock);
        }
    }

    private void addPromotionResponse(final Product product, final List<ProductResponse> productResponses,
                                      final ProductStock productStock
    ) {
        PromotionProduct promotionProduct = promotionProducts.find(product);
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

    public Product findProduct(final String productName) {
        return products.find(Name.from(productName));
    }

    public boolean canReceivePromotion(final Product product, final LocalDateTime createdAt) {
        if (!promotionProducts.exists(product)) {
            return false;
        }
        PromotionProduct promotionProduct = promotionProducts.find(product);
        Promotion promotion = promotions.find(promotionProduct.promotion().promotionType());
        return promotion.isPromotionPeriod(createdAt) &&
                productStorage.hasEnoughPromotionStock(product, promotion.minimumQuantity());
    }

    public boolean hasEnoughStock(final Product product, final int orderQuantity) {
        return productStorage.hasEnoughTotalStock(product, orderQuantity);
    }

    public boolean isPromotionStockShortage(final Product product, final int orderQuantity) {
        return productStorage.isPromotionStockShortage(product, orderQuantity);
    }

    public boolean isCanReceiveFree(final Product product, final int orderQuantity,
                                    final int promotionCount) {
        PromotionProduct promotionProduct = promotionProducts.find(product);
        Promotion promotion = promotions.find(promotionProduct.promotion().promotionType());
        int remain = orderQuantity - (promotion.minimumQuantity() * promotionCount);
        return promotion.isNeedOneMore(remain);
    }

    public int calculatePromotionCount(final Product product, final int orderQuantity) {
        PromotionProduct promotionProduct = promotionProducts.find(product);
        Promotion promotion = promotions.find(promotionProduct.promotion().promotionType());
        int promotionStockQuantity = productStorage.currentPromotionStockQuantity(product);
        return promotion.calculatePromotionCount(promotionStockQuantity, orderQuantity);
    }

    public int calculatePromotionQuantity(final Product product, final int promotionCount) {
        ProductStock productStock = productStorage.find(product);
        PromotionProduct promotionProduct = promotionProducts.find(product);
        Promotion promotion = promotions.find(promotionProduct.promotion().promotionType());
        return promotionCount * promotion.minimumQuantity();
    }

    public boolean isOrderQuantityShortage(final Product product, final int orderQuantity) {
        PromotionProduct promotionProduct = promotionProducts.find(product);
        return promotionProduct.promotion().isOrderQuantityShortage(orderQuantity);
    }

}
