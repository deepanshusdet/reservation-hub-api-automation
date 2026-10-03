package utils;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class AuthUtil {

    public static String getToken() {

        Response response =
                given()
                    .contentType("application/json")
                    .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                .when()
                    .post("/auth");

        return response.jsonPath().getString("token");
    }
}