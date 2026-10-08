# Reservation Hub API Automation

## 1. Project Overview

This project contains automated API tests for the Reservation Hub / Restful Booker API.

The framework is built using **Java, Rest Assured, TestNG and Maven**. The main objective is to validate booking CRUD operations, authentication, negative scenarios and API response contracts.

**Base URL:** `https://restful-booker.herokuapp.com`

---

## 2. Technology Stack

* Java 21
* Rest Assured 5.5.6
* TestNG 7.11.0
* Maven
* Extent Reports
* JSON Schema Validation
* Eclipse IDE

---

## 3. Test Strategy

The test suite follows a combination of:

### Functional Testing

* Health check
* Create booking
* Get booking
* Full booking update using PUT
* Partial booking update using PATCH
* Delete booking
* Verify deleted booking

### Authentication Testing

* Generate authentication token
* Update booking without authentication
* Update booking with invalid authentication
* Delete booking without authentication
* Patch booking without authentication

### Negative and Boundary Testing

The following invalid or boundary conditions are covered:

* Missing required fields
* Empty request body
* Negative total price
* Zero total price
* Wrong data type
* Invalid date
* Checkout date before check-in date
* Non-existing booking ID

### Contract Validation

The Create Booking response is validated using JSON Schema to verify the expected response structure and data types.

---

## 4. Test Flow

The main CRUD flow is:

**Create → Get → PUT → PATCH → Delete → Verify Delete**

The booking ID generated during the Create operation is stored and reused in subsequent operations.

This keeps the CRUD tests connected to the same booking record instead of using hard-coded booking IDs.

---

## 5. Authentication Approach

Authentication is handled through the `/auth` endpoint.

The framework generates a token using the configured credentials and sends the token as a cookie for protected operations.

Credentials are stored in:

```text
src/test/resources/config.properties
```

The credentials are kept outside the test classes so that they can be changed without modifying test code.

---

## 6. Reporting

The project uses **Extent Reports**.

The report provides:

* Overall test execution result
* Individual test status
* Request details
* Response details
* Assertion status
* Failure information
* Test grouping information

Generated report:

```text
test-output/ExtentReport.html
```

---

## 7. Defects Found

Negative testing identified validation issues in the API, including:

* Negative total price being accepted
* Checkout date before check-in being accepted
* Invalid dates being accepted
* Missing required fields resulting in HTTP 500
* Wrong data types being accepted with unexpected response values

Detailed defects are documented separately in:

```text
BUGS.md
```

---

## 8. Shared Environment and Test Data

The API is a shared/public test environment and is not a dedicated environment for this test suite.

Because the environment can contain existing bookings and may be reset periodically, the tests avoid depending on pre-existing booking data wherever possible.

The CRUD flow creates its own booking and uses the returned booking ID for subsequent operations.

The test data is therefore generated during execution instead of relying on a fixed booking ID.

---

## 9. Flakiness and Environment Limitations

The API is hosted in a shared public environment, so occasional issues may occur due to:

* Environment availability
* Cold starts
* Network connectivity
* Shared test data
* Periodic environment resets

The tests use a newly created booking for the CRUD flow to reduce dependency on existing data.

A production-grade implementation could additionally introduce retry/wait handling for transient cold-start or network failures.

---

## 10. Omissions and Assumptions

The following areas were not implemented as full production-level features:

* Parallel execution was not enabled because the API uses shared test data.
* Extensive performance testing was not included because this assignment focuses primarily on functional API automation.
* No database validation was added because database access was not provided.
* No CI/CD pipeline was configured as part of the take-home implementation.
* The test suite uses the public Restful Booker environment, so complete control over environment resets is not available.

For the expected scope of this assignment, priority was given to CRUD coverage, authentication, negative testing, contract validation and reporting.

---

## 11. Project Structure

```text
api-automation
│
├── src/test/java
│   ├── base
│   │   └── BaseTest.java
│   │
│   ├── tests
│   │   ├── HealthTest.java
│   │   ├── AuthTest.java
│   │   ├── BookingTest.java
│   │   ├── AuthNegativeTest.java
│   │   └── BookingNegativeTest.java
│   │
│   └── utils
│       ├── ConfigReader.java
│       ├── AuthUtil.java
│       ├── ExtentManager.java
│       ├── ExtentListener.java
│       ├── ApiLogStore.java
│       └── ExtentRestAssuredFilter.java
│
├── src/test/resources
│   ├── config.properties
│   └── booking-schema.json
│
├── BUGS.md
├── README.md
├── pom.xml
└── test-output
    └── ExtentReport.html
```

---

## 12. How to Run

Open the project as a Maven project in Eclipse.

Run the TestNG test classes from:

```text
src/test/java/tests
```

Alternatively, Maven can be used from the project root:

```bash
mvn test
```

The Extent report will be generated under:

```text
test-output/ExtentReport.html
```

---

## 13. Execution Result

Latest local execution:

* **Total Tests:** 19
* **Passed:** 19
* **Failed:** 0
* **Skipped:** 0

The negative tests intentionally verify the current behavior of the API. Defects discovered during testing are documented in `BUGS.md`.
