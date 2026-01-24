package com.smalakhov.pages;

import com.smalakhov.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class LoginPage {
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getDefaultTimeout()));
        log.debug("Страница логина инициализирована");
    }

    public LoginPage enterUsername(String username) {
        log.info("Ввод имени пользователя: {}", username);
        WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
        usernameElement.clear();
        usernameElement.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        return this;
    }

    public ProductsPage clickLoginButton() {
        log.info("Нажатие кнопки входа");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
        log.debug("Переход на страницу продуктов");
        return new ProductsPage(driver);
    }

    public LoginPage submitLoginForm() {
        log.info("Нажатие кнопки входа (ожидается ошибка)");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
        return this;
    }

    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

    public String getErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(errorMessage));
            String message = errorElement.getText();
            log.info("Сообщение об ошибке на странице логина: {}", message);
            return message;
        } catch (Exception e) {
            log.debug("Сообщение об ошибке не отображается");
            return "";
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            boolean displayed = wait.until(ExpectedConditions.presenceOfElementLocated(errorMessage)).isDisplayed();
            if (displayed) {
                log.info("Отображается сообщение об ошибке авторизации");
            }
            return displayed;
        } catch (Exception e) {
            return false;
        }
    }
}
