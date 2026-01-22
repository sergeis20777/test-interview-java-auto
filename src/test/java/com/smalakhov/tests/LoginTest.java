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
}
