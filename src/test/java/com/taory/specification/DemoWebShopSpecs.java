package com.taory.specification;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.is;

public class DemoWebShopSpecs {
    public static RequestSpecification request = with()
            .filter(new AllureRestAssured())
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .cookie("Nop.customer=4c521896-2c0e-4d82-b6e9-ba01af016c65;");

    public static ResponseSpecification response = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("success", is(true))
            .expectBody("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"))
            .build();
}