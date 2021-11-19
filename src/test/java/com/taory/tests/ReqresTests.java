package com.taory.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.taory.filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @Test
    @DisplayName("Register successful")
    void registerSuccessful() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}")
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Register unsuccessful")
    void registerUnsuccessful() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{\"email\": \"sydney@fife\"}")
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Update put")
    void updatePut() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .put(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(200)
                .body("name", is("morpheus"));
    }

    @Test
    @DisplayName("Update patch")
    void updatePatch() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .patch(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Delete user")
    void delete() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .delete(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(204);
    }
}