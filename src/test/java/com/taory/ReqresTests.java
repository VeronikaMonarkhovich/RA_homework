package com.taory;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {
    @Test
    void registerSuccessful() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}")
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerUnsuccessful() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"sydney@fife\"}")
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void updatePut() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .put(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(200)
                .body("name", is("morpheus"));
    }

    @Test
    void updatePatch() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .patch(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void delete() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .delete(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(204);
    }
}