package com.smalakhov.base;

import com.smalakhov.config.TestConfig;
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

    @BeforeEach
    void setUp(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        log.info("=== Подготовка к тесту: {} ===", testName);

        String browser = TestConfig.getDefaultBrowser();
        boolean headless = TestConfig.isDefaultHeadless();
        log.info("Параметры запуска: браузер={}, headless={}", browser, headless);

        driver = DriverFactory.create(browser, headless);
        wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getDefaultTimeout()));
        log.info("Браузер успешно открыт");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.getDefaultTimeout()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getPageLoadTimeout()));

        driver.get(TestConfig.getBaseUrl());
        log.info("Переход на URL: {}", TestConfig.getBaseUrl());
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
