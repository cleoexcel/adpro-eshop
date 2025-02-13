package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindOneByIdIfEmpty() {
        Product foundProduct = productRepository.findOne(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        assertNull(foundProduct);
    }

    @Test
    void testFindOneByIdIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Product foundProduct1 = productRepository.findOne(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        Product foundProduct2 = productRepository.findOne(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        assertEquals(foundProduct1.getProductId(), product1.getProductId());
        assertEquals(foundProduct2.getProductId(), product2.getProductId());
    }

    @Test
    void testEditAndFind() {
        Product newProduct = new Product();
        newProduct.setProductName("Sampo Cap Bambang");
        newProduct.setProductQuantity(100);
        productRepository.create(newProduct);

        UUID newProductId = newProduct.getProductId();
        Product editedProduct = new Product();
        editedProduct.setProductName("Sampo Cap Usep");
        editedProduct.setProductQuantity(50);
        Product editResultProduct = productRepository.edit(newProductId, editedProduct);
        assertNotEquals(null, editResultProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product foundProduct = productIterator.next();
        assertEquals(foundProduct.getProductId(), newProductId);
        assertEquals(foundProduct.getProductName(), editedProduct.getProductName());
        assertEquals(foundProduct.getProductQuantity(), editedProduct.getProductQuantity());
    }

    @Test
    void testEditNotFound() {
        Product newProduct = new Product();
        newProduct.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        newProduct.setProductName("Sampo Cap Bambang");
        newProduct.setProductQuantity(100);
        productRepository.create(newProduct);

        UUID id = UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        Product editedProduct = new Product();
        editedProduct.setProductId(id);
        editedProduct.setProductName("Sampo Cap Usep");
        editedProduct.setProductQuantity(50);

        Product editResultProduct = productRepository.edit(id, editedProduct);
        assertNull(editResultProduct);
    }

    @Test
    void testDeleteAndFind() {
        Product newProduct = new Product();
        newProduct.setProductName("Sampo Cap Bambang");
        newProduct.setProductQuantity(100);
        productRepository.create(newProduct);

        productRepository.delete(newProduct.getProductId());

        Iterator<Product> allProducts = productRepository.findAll();
        assertFalse(allProducts.hasNext());
    }

    @Test
    void testDeleteNotFound() {
        Product newProduct = new Product();
        newProduct.setProductName("Sampo Cap Bambang");
        newProduct.setProductQuantity(100);
        newProduct.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        productRepository.create(newProduct);

        productRepository.delete(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));

        Iterator<Product> allProducts = productRepository.findAll();
        assertTrue(allProducts.hasNext());
    }
}