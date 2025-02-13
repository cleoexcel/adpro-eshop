package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditProductFunctionalTest {

    @LocalServerPort
    private int serverPort;


    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise
        driver.get(baseUrl + "/product/list");
        String pageTitle = driver.getTitle();
        System.out.println("DEBUG: The page title is: " + pageTitle);
        Assertions.assertEquals("ADV Shop", pageTitle);
    }

    @Test
    void welcomeMessage_homePage_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise
        driver.get(baseUrl + "/product/list");
        String welcomeMessage = driver.findElement(By.tagName("h3"))
                .getText();

        // Verify
        assertEquals("Welcome", welcomeMessage);
    }

    @Test
    void editProduct_isSuccessful(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement nameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nameInput")));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));

        nameInput.clear();
        nameInput.sendKeys("New Product");

        quantityInput.clear();
        quantityInput.sendKeys("10");

        WebElement submitButton = driver.findElement(By.cssSelector("button.btn.btn-primary"));
        submitButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table")));

        WebElement newProductName = driver.findElement(By.xpath("//td[text()='New Product']"));
        assertEquals("New Product", newProductName.getText());

        WebElement editButton = driver.findElement(By.cssSelector("a.btn.btn-info"));
        editButton.click();

        String pageTitle = driver.getTitle();
        Assertions.assertEquals("Edit Product", pageTitle);

        nameInput = driver.findElement(By.id("nameInput"));
        quantityInput = driver.findElement(By.id("quantityInput"));

        nameInput.clear();
        nameInput.sendKeys("Updated Product Name");

        quantityInput.clear();
        quantityInput.sendKeys("99");

        submitButton = driver.findElement(By.cssSelector("button.btn.btn-primary"));
        submitButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table")));

        WebElement updatedProductName = driver.findElement(By.xpath("//td[text()='Updated Product Name']"));
        assertEquals("Updated Product Name", updatedProductName.getText());
    }

}
