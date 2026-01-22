package com.smalakhov.tests;

import com.smalakhov.base.BaseTest;
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
        String username = "standard_user";
        String password = "secret_sauce";
        String expectedPageTitle = "Products";
        String expectedUrl = "https://www.saucedemo.com/inventory.html";

        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(username, password);

        assertTrue(productsPage.isPageLoaded(), "Страница продуктов должна быть загружена");
        assertEquals(expectedPageTitle, productsPage.getPageTitle(), 
                "Заголовок страницы должен быть 'Products'");
        assertEquals(expectedUrl, productsPage.getCurrentUrl(), 
                "URL должен соответствовать странице продуктов");
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("При неверном пароле отображается сообщение об ошибке, переход на страницу продуктов не выполняется")
    @Story("Неуспешная авторизация")
    public void testLoginWithWrongPassword() {
        String username = "standard_user";
        String wrongPassword = "wrong_password";
        String expectedErrorSubstring = "do not match";
        String expectedLoginUrl = "https://www.saucedemo.com/";

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username)
                .enterPassword(wrongPassword)
                .submitLoginForm();

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке при неверном пароле");
        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains(expectedErrorSubstring),
                "Текст ошибки должен содержать '" + expectedErrorSubstring + "', получено: " + errorText);
        assertEquals(expectedLoginUrl, driver.getCurrentUrl(),
                "После ошибки пользователь должен оставаться на странице логина");
    }

    @Test
    @DisplayName("Логин заблокированного пользователя (locked_out_user)")
    @Description("Заблокированный пользователь не может войти: отображается сообщение об ошибке, переход на страницу продуктов не выполняется")
    @Story("Неуспешная авторизация")
    public void testLoginLockedOutUser() {
        String username = "locked_out_user";
        String password = "secret_sauce";
        String expectedErrorSubstring = "locked out";
        String expectedLoginUrl = "https://www.saucedemo.com/";

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username)
                .enterPassword(password)
                .submitLoginForm();

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке для заблокированного пользователя");
        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains(expectedErrorSubstring),
                "Текст ошибки должен содержать '" + expectedErrorSubstring + "', получено: " + errorText);
        assertEquals(expectedLoginUrl, driver.getCurrentUrl(),
                "После ошибки пользователь должен оставаться на странице логина");
    }

    @Test
    @DisplayName("Логин с пустыми полями")
    @Description("При отправке формы без логина и пароля отображается сообщение об ошибке, переход на страницу продуктов не выполняется")
    @Story("Неуспешная авторизация")
    public void testLoginWithEmptyFields() {
        String expectedErrorSubstring = "required";
        String expectedLoginUrl = "https://www.saucedemo.com/";

        LoginPage loginPage = new LoginPage(driver);
        loginPage.submitLoginForm();

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке при пустых полях");
        String errorText = loginPage.getErrorMessage();
        assertTrue(errorText.contains(expectedErrorSubstring),
                "Текст ошибки должен содержать '" + expectedErrorSubstring + "' (например, Username is required), получено: " + errorText);
        assertEquals(expectedLoginUrl, driver.getCurrentUrl(),
                "После ошибки пользователь должен оставаться на странице логина");
    }
}
