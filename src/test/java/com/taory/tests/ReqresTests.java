package com.taory.tests;

import com.taory.model.LombokUserData;
import com.taory.model.RegisterData;
import com.taory.model.UserWithJob;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.taory.specification.ReqresSpecs.request;
import static com.taory.filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests {

    @Test
    @DisplayName("Single user")
    void singleUserWithLombokModel() {
        LombokUserData data = given()
                .filter(customLogFilter().withCustomTemplates())
                .baseUri("https://reqres.in")
                .basePath("/api")
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .extract().as(LombokUserData.class);

        assertEquals("Janet",data.getUser().getFirstName());
    }

    @Test
    @DisplayName("Register successful")
    void registerSuccessful() {
        RegisterData registerData = new RegisterData();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body(registerData)
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Register unsuccessful")
    void registerUnsuccessful() {
        RegisterData registerData = new RegisterData();
        registerData.setEmail("sydney@fife");
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType(JSON)
                .body(registerData)
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Update put")
    void updatePut() {
        UserWithJob user = new UserWithJob();
        user.setName("morpheus");
        user.setJob("zion resident");
        given()
                .spec(request)
                .body(user)
                .when()
                .put(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(200)
                .body("name", is(user.getName()));
    }

    @Test
    @DisplayName("Update patch")
    void updatePatch() {
        UserWithJob user = new UserWithJob();
        user.setName("morpheus");
        user.setJob("zion resident");
        given()
                .spec(request)
                .body(user)
                .when()
                .patch(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(200)
                .body("job", is(user.getJob()));
    }

    @Test
    @DisplayName("Delete user")
    void delete() {
        UserWithJob user = new UserWithJob();
        user.setName("morpheus");
        user.setJob("zion resident");
        given()
                .spec(request)
                .body(user)
                .when()
                .delete(("https://reqres.in/api/users?page=2"))
                .then()
                .statusCode(204);
    }
}