package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Ensures that Mockito is initialized
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;  // Mock the repository

    @InjectMocks
    private ProductServiceImpl productService;  // Inject mocks into the service

    private Product product;
    private UUID productId;

    @BeforeEach
    public void setUp() {
        productId = UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    public void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    public void testEdit() {
        when(productRepository.edit(eq(productId), eq(product))).thenReturn(product);

        Product result = productService.edit(productId, product);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
        verify(productRepository, times(1)).edit(eq(productId), eq(product));
    }

    @Test
    public void testDelete() {
        when(productRepository.delete(productId)).thenReturn(true);

        boolean result = productService.delete(productId);

        assertTrue(result);
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    public void testFindAll() {
        Iterator<Product> productIterator = Collections.singletonList(product).iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        when(productRepository.findOne(productId)).thenReturn(product);

        Product result = productService.findOne(productId);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findOne(productId);
    }
}
