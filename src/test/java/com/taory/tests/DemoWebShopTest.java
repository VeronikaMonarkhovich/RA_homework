package com.taory.tests;

import com.codeborne.selenide.CollectionCondition;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Cookie;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.taory.specification.DemoWebShopSpecs.request;
import static com.taory.specification.DemoWebShopSpecs.response;
import static io.restassured.RestAssured.given;

public class DemoWebShopTest {

    String authorizationCookie;

    @BeforeEach
    @DisplayName("Authorization and checking that the Wishlist is empty")
    public void beforeEach() {
        authorizationCookie = given()
                .filter(new AllureRestAssured())
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", "loly@gmail.com")
                .formParam("Password", "111111")
                .when()
                .post("http://demowebshop.tricentis.com/login")
                .then()
                .statusCode(302)
                .extract()
                .cookie("NOPCOMMERCE.AUTH");

        open("/Themes/DefaultClean/Content/images/logo.png");
        getWebDriver()
                .manage()
                .addCookie(new Cookie("NOPCOMMERCE.AUTH", authorizationCookie));

        open("http://demowebshop.tricentis.com/wishlist");
        $(".wishlist-qty").shouldHave(text("(0)"));
    }

    @Test
    @DisplayName("Add products to Wishlist")

    public void addProductsToWishlist() {
        given()
                .spec(request)
                .body("addtocart_43.EnteredQuantity=1")
                .when()
                .cookie(authorizationCookie)
                .post("http://demowebshop.tricentis.com/addproducttocart/details/43/2")
                .then()
                .spec(response);

        given()
                .spec(request)
                .body("product_attribute_80_2_37=112&product_attribute_80_1_38=114&" +
                        "addtocart_80.EnteredQuantity=1")
                .when()
                .cookie(authorizationCookie)
                .post("http://demowebshop.tricentis.com/addproducttocart/details/80/2")
                .then()
                .spec(response);

        getWebDriver()
                .manage()
                .addCookie(new Cookie("NOPCOMMERCE.AUTH", authorizationCookie));

        open("http://demowebshop.tricentis.com/wishlist");
        $(".wishlist-qty").shouldHave(text("(2)"));
        $$(".cart-item-row").shouldHave(CollectionCondition.size(2));
    }
}