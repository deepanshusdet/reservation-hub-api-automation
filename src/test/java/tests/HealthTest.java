package tests;

import org.testng.annotations.Test;

import base.BaseTest;

import static io.restassured.RestAssured.*;

public class HealthTest extends BaseTest {

    @Test
    public void checkHealth() {

        given()
            .when()
            .get("/ping")
            .then()
            .statusCode(201);
    }
}