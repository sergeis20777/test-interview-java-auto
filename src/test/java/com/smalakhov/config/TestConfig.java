package com.smalakhov.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class TestConfig {
    private static final Logger log = LoggerFactory.getLogger(TestConfig.class);
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                log.warn("Файл config.properties не найден, будут использованы значения по умолчанию");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            log.error("Ошибка при загрузке config.properties: {}", e.getMessage());
        }
    }

    private static final String BASE_URL = getProperty("base.url");
    private static final String PRODUCTS_URL = getProperty("products.url");

    private static final int DEFAULT_TIMEOUT = Integer.parseInt(getProperty("timeout.default"));
    private static final int PAGE_LOAD_TIMEOUT = Integer.parseInt(getProperty("timeout.pageLoad"));

    private static final String DEFAULT_BROWSER = getProperty("browser");
    private static final boolean DEFAULT_HEADLESS = Boolean.parseBoolean(getProperty("headless"));

    public static class Users {
        public static final String STANDARD_USER = getProperty("user.standard.username");
        public static final String LOCKED_OUT_USER = getProperty("user.lockedOut.username");
        public static final String PERFORMANCE_GLITCH_USER = getProperty("user.performanceGlitch.username");
        public static final String DEFAULT_PASSWORD = getProperty("user.default.password");
    }

    public static class ExpectedMessages {
        public static final String PRODUCTS_PAGE_TITLE = "Products";
        public static final String ERROR_WRONG_CREDENTIALS = "do not match";
        public static final String ERROR_LOCKED_OUT = "locked out";
        public static final String ERROR_REQUIRED_FIELD = "required";
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getProductsUrl() {
        return PRODUCTS_URL;
    }

    public static int getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }

    public static int getPageLoadTimeout() {
        return PAGE_LOAD_TIMEOUT;
    }

    public static String getDefaultBrowser() {
        return DEFAULT_BROWSER;
    }

    public static boolean isDefaultHeadless() {
        return DEFAULT_HEADLESS;
    }

    private static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            log.debug("Использование переопределенного значения из System Property для '{}': {}", key, systemValue);
            return systemValue;
        }

        String propertyValue = properties.getProperty(key);
        if (propertyValue != null) {
            return propertyValue;
        }

        log.warn("Параметр '{}' не найден ни в config.properties, ни в System Properties", key);
        return "";
    }
}
