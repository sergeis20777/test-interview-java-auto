package com.smalakhov.tests;

import com.smalakhov.base.BaseTest;
import com.smalakhov.config.TestConfig;
import com.smalakhov.pages.LoginPage;
import com.smalakhov.pages.ProductsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Epic("Авторизация")
@Feature("Логин пользователя")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Успешный логин с валидными учетными данными")
    @Description("Проверка успешного входа в систему с использованием стандартного пользователя")
    @Story("Успешная авторизация")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
                TestConfig.Users.STANDARD_USER, 
                TestConfig.Users.DEFAULT_PASSWORD
        );

        assertTrue(productsPage.isPageLoaded(), "Страница продуктов должна быть загружена");
        assertEquals(TestConfig.ExpectedMessages.PRODUCTS_PAGE_TITLE, productsPage.getPageTitle(), 
                "Заголовок страницы должен быть 'Products'");
        assertEquals(TestConfig.getProductsUrl(), productsPage.getCurrentUrl(),
                "URL должен соответствовать странице продуктов");
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("При неверном пароле отображается сообщение об ошибке, переход на страницу продуктов не выполняется")
    @Story("Неуспешная авторизация")
    public void testLoginWithWrongPassword() {
        String wrongPassword = "wrong_password";

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(TestConfig.Users.STANDARD_USER)
                .enterPassword(wrongPassword)
                .submitLoginForm();

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке при неверном пароле");
        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains(TestConfig.ExpectedMessages.ERROR_WRONG_CREDENTIALS),
                "Текст ошибки должен содержать '" + TestConfig.ExpectedMessages.ERROR_WRONG_CREDENTIALS + "', получено: " + errorText);
        assertEquals(TestConfig.getBaseUrl(), driver.getCurrentUrl(),
                "После ошибки пользователь должен оставаться на странице логина");
    }

    @Test
    @DisplayName("Логин заблокированного пользователя (locked_out_user)")
    @Description("Заблокированный пользователь не может войти: отображается сообщение об ошибке, переход на страницу продуктов не выполняется")
    @Story("Неуспешная авторизация")
    public void testLoginLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(TestConfig.Users.LOCKED_OUT_USER)
                .enterPassword(TestConfig.Users.DEFAULT_PASSWORD)
                .submitLoginForm();

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке для заблокированного пользователя");
        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains(TestConfig.ExpectedMessages.ERROR_LOCKED_OUT),
                "Текст ошибки должен содержать '" + TestConfig.ExpectedMessages.ERROR_LOCKED_OUT + "', получено: " + errorText);
        assertEquals(TestConfig.getBaseUrl(), driver.getCurrentUrl(),
                "После ошибки пользователь должен оставаться на странице логина");
    }

    @Test
    @DisplayName("Логин с пустыми полями")
    @Description("При отправке формы без логина и пароля отображается сообщение об ошибке, переход на страницу продуктов не выполняется")
    @Story("Неуспешная авторизация")
    public void testLoginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.submitLoginForm();

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке при пустых полях");
        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains(TestConfig.ExpectedMessages.ERROR_REQUIRED_FIELD),
                "Текст ошибки должен содержать '" + TestConfig.ExpectedMessages.ERROR_REQUIRED_FIELD + "' (например, Username is required), получено: " + errorText);
        assertEquals(TestConfig.getBaseUrl(), driver.getCurrentUrl(),
                "После ошибки пользователь должен оставаться на странице логина");
    }

    @Test
    @DisplayName("Логин пользователем performance_glitch_user")
    @Description("Проверка корректного перехода на страницу продуктов при логине пользователем с имитацией задержек; страница должна открываться несмотря на возможные задержки")
    @Story("Успешная авторизация")
    public void testLoginPerformanceGlitchUser() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
                TestConfig.Users.PERFORMANCE_GLITCH_USER, 
                TestConfig.Users.DEFAULT_PASSWORD
        );

        assertTrue(productsPage.isPageLoaded(), "Страница продуктов должна быть загружена несмотря на задержки");
        assertEquals(TestConfig.ExpectedMessages.PRODUCTS_PAGE_TITLE, productsPage.getPageTitle(),
                "Заголовок страницы должен быть 'Products'");
        assertEquals(TestConfig.getProductsUrl(), productsPage.getCurrentUrl(),
                "URL должен соответствовать странице продуктов");
    }
}
