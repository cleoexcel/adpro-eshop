package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Product edit(UUID productId, Product updatedProduct) {
        for (Product existingProduct : productData)  {
            if (existingProduct.getProductId().equals(productId)) {
                if (updatedProduct.getProductName() != null) {
                    existingProduct.setProductName(updatedProduct.getProductName());
                }

                if (updatedProduct.getProductQuantity() != 0) {
                    existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
                }

                return existingProduct;
            }
        }

        return null;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findOne(UUID productId) {
        for (Product existingProduct : productData)  {
            if (existingProduct.getProductId().equals(productId)) {
                return existingProduct;
            }
        }

        return null;
    }
}