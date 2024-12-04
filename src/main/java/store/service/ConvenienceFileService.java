package store.service;

import java.util.List;

import store.domain.Product;
import store.domain.Products;
import store.presentation.dto.ProductSaveRequest;

public class ConvenienceFileService {

    private final Products products = new Products();

    public void saveProducts(final List<ProductSaveRequest> productSaveRequests) {
        for (ProductSaveRequest productSaveRequest : productSaveRequests) {
            Product product = new Product(productSaveRequest.name(), productSaveRequest.price());
            products.save(product);
        }
    }

    public List<Product> getProducts() {
        return products.getProducts();
    }

}
