package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;
    private Product product;
    private UUID productId;

    @BeforeEach
    public void setUp() {
        productId = UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6");  // Initialize productId here
        this.product = new Product();
        product.setProductId(productId); // Set the initialized productId
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    public void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    public void testCreateProductPost() {
        when(productService.create(any(Product.class))).thenReturn(product);

        String viewName = productController.createProductPost(product, model);

        assertEquals("redirect:list", viewName);
        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    public void testProductListPage() {
        List<Product> products = Collections.singletonList(product);
        when(productService.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        assertEquals("ProductList", viewName);
        verify(model, times(1)).addAttribute(eq("products"), eq(products));
    }

    @Test
    public void testEditProductPage() {
        when(productService.findOne(productId)).thenReturn(product);

        String viewName = productController.editProductPage(productId, model);

        assertEquals("EditProduct", viewName);
        verify(model, times(1)).addAttribute(eq("productToEdit"), eq(product));
    }

    @Test
    public void testEditProductPatch() {
        when(productService.edit(eq(productId), any(Product.class))).thenReturn(product);

        String viewName = productController.editProductPatch(productId, product, model);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).edit(eq(productId), any(Product.class));
    }

    @Test
    public void testDeleteProductPath() {

        when(productService.delete(eq(productId))).thenReturn(true);


        String viewName = productController.deleteProductPath(productId, model);

        assertEquals("redirect:/product/list", viewName);

        verify(productService, times(1)).delete(eq(productId));
    }
}
