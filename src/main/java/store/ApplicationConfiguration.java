package store;

import store.domain.inventory.Name;
import store.domain.inventory.Product;
import store.domain.inventory.ProductStock;
import store.domain.inventory.Promotion;
import store.domain.inventory.PromotionProduct;
import store.domain.inventory.PromotionType;
import store.presentation.client.inventory.InventoryClient;
import store.presentation.client.sales.SalesClient;
import store.presentation.file.FileInput;
import store.presentation.view.InputView;
import store.presentation.view.OutputView;
import store.repository.Repository;
import store.repository.inventory.ProductRepository;
import store.repository.inventory.ProductStockRepository;
import store.repository.inventory.PromotionProductRepository;
import store.repository.inventory.PromotionRepository;
import store.service.inventory.InventoryService;
import store.service.sales.SalesService;

public class ApplicationConfiguration {

    public InventoryClient inventoryClient() {
        return new InventoryClient(fileInput(), inventoryService());
    }

    public SalesClient orderClient() {
        return new SalesClient(salesService());
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    private FileInput fileInput() {
        return new FileInput();
    }

    private InventoryService inventoryService() {
        return new InventoryService(
                productRepository(), productStockRepository(), promotionRepository(), promotionProductsRepository()
        );
    }

    private SalesService salesService() {
        return new SalesService(inventoryService());
    }

    private Repository<Product, Name> productRepository() {
        return new ProductRepository();
    }

    private Repository<ProductStock, Product> productStockRepository() {
        return new ProductStockRepository();
    }

    private Repository<Promotion, PromotionType> promotionRepository() {
        return new PromotionRepository();
    }

    private Repository<PromotionProduct, Product> promotionProductsRepository() {
        return new PromotionProductRepository();
    }

}
