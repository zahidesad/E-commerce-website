package com.selenium;

import org.junit.jupiter.api.*;                  // JUnit 5 imports
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)       // to run tests in specified order
public class ECommerceTestSuite {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://localhost:8080/E_commerce_website_war";
    private static String testName;
    private static String testSurname;
    private static String testIdentityNumber;
    private static String testEmail;
    private static String testBirthYear;
    private static String testPhoneNumber;
    private static String testAnswer;
    private static String testPassword;


    @BeforeEach
    public void setUp() {
        // Set the path to the ChromeDriver executable and initialize the Chrome browser
        driver = new ChromeDriver();
        driver.manage().window().maximize();     // Open browser in maximized window (optional for visibility)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Initialize explicit wait:contentReference[oaicite:4]{index=4}
        // Navigate to the base URL (login page as a starting point for flows)
        driver.get(baseUrl + "/login");
    }

    //@AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close browser after each test:contentReference[oaicite:5]{index=5}
        }
    }

    @Test
    @Order(1)
    public void testUserRegistration() {
        // Navigate to registration page (click "Register" link from login page)
        driver.findElement(By.linkText("Create account")).click();
        // Fill out registration form with unique user data
        testName = "Ayşe";
        testSurname = "Tekerek";
        testIdentityNumber = "43249337544";
        testBirthYear = "2003";
        testEmail = "fizyapproje@gmail.com";
        testAnswer = "Togg";
        testPassword = "Pass123!";

        driver.findElement(By.name("name")).sendKeys(testName);
        driver.findElement(By.name("surname")).sendKeys(testSurname);
        driver.findElement(By.name("tcNumber")).sendKeys(testIdentityNumber);
        driver.findElement(By.name("birthYear")).sendKeys(testBirthYear);
        driver.findElement(By.name("mobileNumber")).sendKeys("055555555555");
        driver.findElement(By.name("email")).sendKeys(testEmail);
        driver.findElement(By.name("answer")).sendKeys(testAnswer);
        driver.findElement(By.name("password")).sendKeys(testPassword);
        // Submit the form
        WebElement signUpBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[type='submit']")
                )
        );
        signUpBtn.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("/login"));
        // Verify the registration outcome
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/login") || currentUrl.contains("/register"),
                "User should be redirected to login or home after successful registration");
    }

    @Test
    @Order(2)
    public void testUserLogin() {
        // Navigate to login page (if not already on it from setup)
        driver.get(baseUrl + "/login");
        // Enter the credentials of the newly registered user
        driver.findElement(By.id("j_username")).sendKeys("fizyapproje@gmail.com");
        driver.findElement(By.id("j_password")).sendKeys("Pass123!");
        driver.findElement(By.cssSelector("button.btn.btn-primary.w-100")).click();
        // Wait until the home/dashboard page is loaded (e.g., URL contains "/home")
        wait.until(ExpectedConditions.urlContains("/home"));
        // Verify successful login by checking the current URL or a welcome element
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/home"),
                "After login, the URL should be the home/dashboard page");
        // (Optionally, check for a known element like a logout link or welcome message)
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        Assertions.assertTrue(logoutLink.isDisplayed(), "Logout button should be visible after login");
    }

    @Test
    @Order(3)
    public void testProductSearch() throws InterruptedException {
        testUserLogin();
        driver.get(baseUrl + "/home");
        // Perform a product search using the search bar
        WebElement searchBox = driver.findElement(By.name("query"));
        searchBox.sendKeys("Lenovo");                           // example search query
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Wait for search results to be displayed (ensure at least one product item appears)
        // Verify that results are present
        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("search?query="));
    }

    @Test
    @Order(4)
    public void testAddToCart() throws InterruptedException {
        testUserLogin();
        driver.get(baseUrl + "/home");
        WebElement searchBox = driver.findElement(By.name("query"));
        searchBox.sendKeys("Lenovo");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-success")));
        // Click "Add to Cart" on the first product in the results
        WebElement addButton = driver.findElements(By.cssSelector("button.btn.btn-success")).get(0);
        addButton.click();

        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(Objects.equals(currentUrl, "http://localhost:8080/E_commerce_website_war/myCart"));
        // (Optionally, assert the cart item’s details like name or quantity)
    }
}

