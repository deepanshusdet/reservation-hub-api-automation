# Reservation Hub API Automation

API automation project for Reservation Hub / Restful Booker.

## Tools

* Java 21
* Rest Assured
* TestNG
* Maven
* Extent Reports

## Testing Covered

* Health check
* Create / Get booking
* PUT / PATCH update
* Delete booking
* Authentication
* Negative testing
* Response and JSON Schema validation

## Negative Tests

Tested invalid inputs like:

* Missing fields
* Empty request
* Negative / zero price
* Wrong data type
* Invalid dates
* Checkout before check-in
* Invalid booking ID

Some validation issues were found and documented in `BUGS.md`.

## Report

Extent Report:

`test-output/ExtentReport.html`

## Test Flow

Create → Get → PUT → PATCH → Delete → Verify Delete

The booking ID is taken from the Create response.

## Run

Open the project as a Maven project in Eclipse and run the TestNG tests.
