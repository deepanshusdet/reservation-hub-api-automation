package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ExtentRestAssuredFilter implements Filter {

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext context) {

        // Request details
        String requestBody = "";

        if (requestSpec.getBody() != null) {
            requestBody = requestSpec.getBody().toString();
        }

        ApiLogStore.add(
                "REQUEST\n"
                + requestSpec.getMethod()
                + " "
                + requestSpec.getURI()
                + "\n"
                + requestBody
        );

        // Send request
        Response response = context.next(requestSpec, responseSpec);

        // Response details
        String responseBody = response.asPrettyString();

        // Hide authentication token
        responseBody = responseBody.replaceAll(
                "(\"token\"\\s*:\\s*\")[^\"]+(\")",
                "$1***$2"
        );

        ApiLogStore.add(
                "RESPONSE\n"
                + "Status Code: "
                + response.getStatusCode()
                + "\n"
                + responseBody
        );

        return response;
    }
}
