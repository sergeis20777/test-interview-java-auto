package com.smalakhov.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL = "https://www.saucedemo.com/";
    protected static final int TIMEOUT_SEC = 10;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        log.info("=== Подготовка к тесту: {} ===", testName);

        String browser = System.getProperty("browser", "yandex");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        log.info("Параметры запуска: браузер={}, headless={}", browser, headless);

        driver = DriverFactory.create(browser, headless);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SEC));
        log.info("Браузер успешно открыт");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_SEC));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(BASE_URL);
        log.info("Переход на URL: {}", BASE_URL);
        log.info("Тест запущен: {}", testName);
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        if (driver != null) {
            driver.quit();
            log.info("Браузер закрыт");
        }
        log.info("=== Тест выполнен: {} ===\n", testName);
    }
}
