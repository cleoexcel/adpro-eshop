package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable UUID productId, Model model) {
        Product productToEdit = service.findOne(productId);
        model.addAttribute("productToEdit", productToEdit);
        return "EditProduct";
    }

    @PatchMapping("/edit/{productId}")
    public String editProductPatch(@PathVariable UUID productId, @ModelAttribute Product editedProduct, Model model) {
        service.edit(productId, editedProduct);
        return "redirect:/product/list";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProductPath(@PathVariable UUID productId, Model model) {
        service.delete(productId);
        return "redirect:/product/list";
    }
}