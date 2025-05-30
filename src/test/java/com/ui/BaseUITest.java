package com.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseUITest {

    protected static WebDriver driver;
    protected static final String BASE = "http://localhost:8080/E_commerce_website_war";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) driver.quit();
    }
}