package tests;

import org.testng.annotations.Test;

import base.BaseTest;

import static io.restassured.RestAssured.*;

public class AuthNegativeTest extends BaseTest {

    @Test
    public void updateWithoutToken() {

        String body = """
                {
                    "firstname": "Test",
                    "lastname": "User",
                    "totalprice": 500,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2026-10-05",
                        "checkout": "2026-10-10"
                    }
                }
                """;

        given()
            .contentType("application/json")
            .body(body)
        .when()
            .put("/booking/1")
        .then()
            .statusCode(403);
    }
    @Test
    public void updateWithInvalidToken() {

        String body = """
                {
                    "firstname": "Test",
                    "lastname": "User",
                    "totalprice": 500,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2026-10-05",
                        "checkout": "2026-10-10"
                    }
                }
                """;

        given()
            .contentType("application/json")
            .cookie("token", "invalid-token")
            .body(body)
        .when()
            .put("/booking/1")
        .then()
            .statusCode(403);
    }
}