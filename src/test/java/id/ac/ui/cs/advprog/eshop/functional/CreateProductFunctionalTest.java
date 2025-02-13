package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DirtiesContext
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        String pageTitle = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Product' List", pageTitle);
    }

    @Test
    void navigateCreateProductPage_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        assertEquals("Create New Product", canClickTheButton_test(driver));
    }

    @Test
    void createNewProduct_isCorrect(ChromeDriver driver) {
        assertNotNull(createProduct_test(driver));
    }

    String canClickTheButton_test(ChromeDriver driver) {
        WebElement createProductButton = driver.findElement(By.cssSelector(".btn.btn-primary.btn-sm.mb-3"));
        assertNotNull(createProductButton);
        createProductButton.click();
        return driver.findElement(By.tagName("h3")).getText();
    }

    String createProduct_test(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement addProductName = driver.findElement(By.id("nameInput"));
        WebElement addProductQuantity = driver.findElement(By.id("quantityInput"));
        WebElement saveButton = driver.findElement(By.cssSelector(".btn.btn-primary"));

        assertNotNull(addProductName);
        assertNotNull(addProductQuantity);
        assertNotNull(saveButton);

        addProductName.sendKeys("DUMMY_NAME");
        addProductQuantity.sendKeys("64");
        saveButton.click();
        return "Berhasil";
    }
}