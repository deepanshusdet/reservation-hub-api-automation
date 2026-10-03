package tests;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Listeners;
import utils.ExtentListener;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import utils.AuthUtil;

@Listeners(ExtentListener.class)
public class BookingTest extends BaseTest {
	int bookingId;

	@Test
	public void createBooking() {

	    String body = """
	            {
	                "firstname": "Deepanshu",
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

	    System.out.println(response.asPrettyString());

	    response.then()
	        .statusCode(200)
	        .body("bookingid", org.hamcrest.Matchers.notNullValue())
	        .body("booking.firstname", org.hamcrest.Matchers.notNullValue())
	        .body("booking.lastname", org.hamcrest.Matchers.notNullValue())
	        .body("booking.totalprice", org.hamcrest.Matchers.notNullValue())
	        .body("booking.depositpaid", org.hamcrest.Matchers.notNullValue())
	        .body("booking.bookingdates.checkin", org.hamcrest.Matchers.notNullValue())
	        .body("booking.bookingdates.checkout", org.hamcrest.Matchers.notNullValue())
	        .body(matchesJsonSchemaInClasspath("booking-schema.json"));

	    bookingId = response.jsonPath().getInt("bookingid");

	    System.out.println("Booking ID: " + bookingId);
	}
	@Test(dependsOnMethods = "createBooking")
	public void getBooking() 
	{

		given()
		.when()
		.get("/booking/" + bookingId)
		.then()
		.statusCode(200)
		.body("firstname", org.hamcrest.Matchers.equalTo("Deepanshu"))
		.body("lastname", org.hamcrest.Matchers.equalTo("Sharma"))
		.body("totalprice", org.hamcrest.Matchers.equalTo(500));
	}
	@Test(dependsOnMethods = "getBooking")
	public void updateBooking() {

		String token = AuthUtil.getToken();

		String body = """
				{
				    "firstname": "Rahul",
				    "lastname": "Sharma",
				    "totalprice": 700,
				    "depositpaid": true,
				    "bookingdates": {
				        "checkin": "2026-10-06",
				        "checkout": "2026-10-12"
				    },
				    "additionalneeds": "Lunch"
				}
				""";

		given()
		.contentType("application/json")
		.cookie("token", token)
		.body(body)
		.when()
		.put("/booking/" + bookingId)
		.then()
		.statusCode(200)
		.body("firstname", org.hamcrest.Matchers.equalTo("Rahul"))
		.body("lastname", org.hamcrest.Matchers.equalTo("Sharma"))
		.body("totalprice", org.hamcrest.Matchers.equalTo(700));



	}

	@Test(dependsOnMethods = "updateBooking")
	public void patchBooking() {

		String token = AuthUtil.getToken();

		String body = """
				{
				    "firstname": "Amit"
				}
				""";

		given()
		.contentType("application/json")
		.cookie("token", token)
		.body(body)
		.when()
		.patch("/booking/" + bookingId)
		.then()
		.statusCode(200)
		.body("firstname", org.hamcrest.Matchers.equalTo("Amit"));
	}

	@Test(dependsOnMethods = "patchBooking")
	public void deleteBooking() {

		String token = AuthUtil.getToken();

		given()
		.cookie("token", token)
		.when()
		.delete("/booking/" + bookingId)
		.then()
		.statusCode(201);

		given()
		.when()
		.get("/booking/" + bookingId)
		.then()
		.statusCode(404);
	}
}