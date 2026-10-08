# Bugs Found

This document contains defects identified during negative and boundary testing of the Reservation Hub / Restful Booker API.

---

# BUG-01 — Negative Total Price Is Accepted

**Severity:** High

**API:** `POST /booking`

**Description:**
The API accepts a negative value for `totalprice` and successfully creates a booking.

### Request

```json
{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": -500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-05",
    "checkout": "2026-10-10"
  }
}
```

### cURL

```bash
curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": -500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-05",
    "checkout": "2026-10-10"
  }
}'
```

### Expected Result

The API should reject the request with a validation error because a booking price cannot be negative.

### Actual Result

The API returned `200 OK` and created the booking with:

```json
"totalprice": -500
```

### Impact

Invalid booking amounts can be stored in the system and may affect booking or payment calculations.

---

# BUG-02 — Checkout Date Before Check-In Date Is Accepted

**Severity:** High

**API:** `POST /booking`

**Description:**
The API accepts a booking where the checkout date is earlier than the check-in date.

### Request

```json
{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": 500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-10",
    "checkout": "2026-10-05"
  }
}
```

### cURL

```bash
curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": 500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-10",
    "checkout": "2026-10-05"
  }
}'
```

### Expected Result

The API should reject the request because the checkout date cannot be earlier than the check-in date.

### Actual Result

The API returned `200 OK` and created the booking with the invalid date range.

### Impact

This can result in logically invalid reservations being stored in the system.

---

# BUG-03 — Invalid Date Is Accepted

**Severity:** High

**API:** `POST /booking`

**Description:**
The API accepts an invalid date value instead of rejecting the request.

### Request

```json
{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": 500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-99-99",
    "checkout": "2026-10-10"
  }
}
```

### cURL

```bash
curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": 500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-99-99",
    "checkout": "2026-10-10"
  }
}'
```

### Expected Result

The API should reject the request with a validation error because `2026-99-99` is not a valid date.

### Actual Result

The API returned `200 OK` and accepted the request.

The response stored the check-in date as:

```json
"checkin": "0NaN-aN-aN"
```

### Impact

Invalid date values can lead to corrupted booking data and potential downstream date-processing issues.

---

# BUG-04 — Missing Firstname Returns HTTP 500

**Severity:** High

**API:** `POST /booking`

**Description:**
When the required `firstname` field is missing, the API returns a server error instead of a client validation error.

### Request

```json
{
  "lastname": "Sharma",
  "totalprice": 500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-05",
    "checkout": "2026-10-10"
  }
}
```

### cURL

```bash
curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
  "lastname": "Sharma",
  "totalprice": 500,
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-05",
    "checkout": "2026-10-10"
  }
}'
```

### Expected Result

The API should return a proper client-side validation error, such as `400 Bad Request`, indicating that `firstname` is required.

### Actual Result

The API returned:

```text
500 Internal Server Error
```

### Impact

Invalid client input is incorrectly treated as a server error, making the API behavior less predictable for consumers.

---

# BUG-05 — Empty Request Returns HTTP 500

**Severity:** High

**API:** `POST /booking`

**Description:**
The API returns a server error when an empty request body is submitted.

### Request

```json
{}
```

### cURL

```bash
curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{}'
```

### Expected Result

The API should return a proper client-side validation error, such as `400 Bad Request`.

### Actual Result

The API returned:

```text
500 Internal Server Error
```

### Impact

An invalid request from the client should not result in a server-side error.

---

# BUG-06 — Wrong Data Type Results in Null Total Price

**Severity:** Medium

**API:** `POST /booking`

**Description:**
The API accepts a string value for `totalprice`, even though the field should contain a numeric value. Instead of rejecting the request, it stores the value as `null`.

### Request

```json
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
```

### cURL

```bash
curl -X POST "https://restful-booker.herokuapp.com/booking" \
-H "Content-Type: application/json" \
-d '{
  "firstname": "Deepanshu",
  "lastname": "Sharma",
  "totalprice": "five hundred",
  "depositpaid": true,
  "bookingdates": {
    "checkin": "2026-10-05",
    "checkout": "2026-10-10"
  }
}'
```

### Expected Result

The API should reject the request with a validation error because `totalprice` should be a numeric value.

### Actual Result

The API returned `200 OK` and the response contained:

```json
"totalprice": null
```

### Impact

Silently accepting an invalid data type can result in incomplete or inconsistent booking data.

---

# Boundary Observation — Zero Total Price

**API:** `POST /booking`

### Test

A booking was submitted with:

```json
"totalprice": 0
```

### Actual Result

The API accepted the request and returned `200 OK`.

### Classification

This is recorded as a **boundary observation rather than a confirmed defect**, because the assignment does not explicitly define whether a zero booking price should be considered invalid.

---

# Summary

| ID     | Issue                                       | Severity |
| ------ | ------------------------------------------- | -------- |
| BUG-01 | Negative total price accepted               | High     |
| BUG-02 | Checkout before check-in accepted           | High     |
| BUG-03 | Invalid date accepted                       | High     |
| BUG-04 | Missing firstname returns HTTP 500          | High     |
| BUG-05 | Empty request returns HTTP 500              | High     |
| BUG-06 | Wrong data type results in null total price | Medium   |

**Total confirmed defects: 6**

**Additional boundary observation: 1**
