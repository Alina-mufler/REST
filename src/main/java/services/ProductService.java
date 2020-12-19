package services;

import models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProducts();
    Optional<Product> getProduct(Product product);
    void deleteProduct(Product product);
    void deleteProducts();
    void putProduct(Product product);
    void updateProduct(Product oldProduct, Product newProduct);
}
