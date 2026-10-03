package tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;

public class BookingNegativeTest extends BaseTest {

	@Test
	public void createBookingWithoutFirstname() {

		String body = """
				{
				    "lastname": "Sharma",
				    "totalprice": 500,
				    "depositpaid": true,
				    "bookingdates": {
				        "checkin": "2026-10-05",
				        "checkout": "2026-10-10"
				    },
				    "additionalneeds": "Breakfast"
				}
				""";

		Response response =
				given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		System.out.println("Status: " + response.statusCode());
		System.out.println("Response: " + response.asPrettyString());
	}

	@Test
	public void createBookingWithNegativePrice() {

		String body = """
				{
				    "firstname": "Deepanshu",
				    "lastname": "Sharma",
				    "totalprice": -500,
				    "depositpaid": true,
				    "bookingdates": {
				        "checkin": "2026-10-05",
				        "checkout": "2026-10-10"
				    },
				    "additionalneeds": "Breakfast"
				}
				""";

		Response response =
				given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		System.out.println("Status: " + response.statusCode());
		System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void createBookingWithCheckoutBeforeCheckin() {

		String body = """
				{
				    "firstname": "Deepanshu",
				    "lastname": "Sharma",
				    "totalprice": 500,
				    "depositpaid": true,
				    "bookingdates": {
				        "checkin": "2026-10-10",
				        "checkout": "2026-10-05"
				    },
				    "additionalneeds": "Breakfast"
				}
				""";

		Response response =
				given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		System.out.println("Status: " + response.statusCode());
		System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void createBookingWithZeroPrice() {

		String body = """
				{
				    "firstname": "Deepanshu",
				    "lastname": "Sharma",
				    "totalprice": 0,
				    "depositpaid": true,
				    "bookingdates": {
				        "checkin": "2026-10-05",
				        "checkout": "2026-10-10"
				    },
				    "additionalneeds": "Breakfast"
				}
				""";

		Response response =
				given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		System.out.println("Status: " + response.statusCode());
		System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void createBookingWithMalformedDate() {

		String body = """
				{
				    "firstname": "Deepanshu",
				    "lastname": "Sharma",
				    "totalprice": 500,
				    "depositpaid": true,
				    "bookingdates": {
				        "checkin": "2026-99-99",
				        "checkout": "2026-10-10"
				    },
				    "additionalneeds": "Breakfast"
				}
				""";

		Response response =
				given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		System.out.println("Status: " + response.statusCode());
		System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void createBookingWithEmptyPayload() {

		String body = "{}";

		Response response =
				given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/booking");

		System.out.println("Status: " + response.statusCode());
		System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void createBookingWithWrongDataType() {

	    String body = """
	            {
	                "firstname": "Deepanshu",
	                "lastname": "Sharma",
	                "totalprice": "five hundred",
	                "depositpaid": true,
	                "bookingdates": {
	                    "checkin": "2026-10-05",
	                    "checkout": "2026-10-10"
	                }
	            }
	            """;

	    Response response =
	            given()
	                .contentType("application/json")
	                .body(body)
	            .when()
	                .post("/booking");

	    System.out.println("Status: " + response.statusCode());
	    System.out.println("Response: " + response.asPrettyString());
	}
	
	@Test
	public void getNonExistentBooking() {

	    Response response =
	            given()
	            .when()
	                .get("/booking/999999");

	    System.out.println("Status: " + response.statusCode());
	    System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void patchWithoutToken() {

	    String body = """
	            {
	                "firstname": "Test"
	            }
	            """;

	    Response response =
	            given()
	                .contentType("application/json")
	                .body(body)
	            .when()
	                .patch("/booking/1");

	    System.out.println("Status: " + response.statusCode());
	    System.out.println("Response: " + response.asPrettyString());
	}
	@Test
	public void deleteWithoutToken() {

	    Response response =
	            given()
	            .when()
	                .delete("/booking/1");

	    System.out.println("Status: " + response.statusCode());
	    System.out.println("Response: " + response.asPrettyString());
	}
}
