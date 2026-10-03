# Bugs Found

## BUG-01: Negative total price is accepted

**Severity:** High

**API:** POST /booking

**Request:**

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

**Expected:**
API should reject a negative total price.

**Actual:**
API returned `200` and created the booking with `totalprice: -500`.

---

## BUG-02: Checkout date before check-in date is accepted

**Severity:** High

**API:** POST /booking

**Request:**

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

**Expected:**
API should reject the request because checkout is before check-in.

**Actual:**
API returned `200` and created the booking.

---

## BUG-03: Invalid date is accepted

**Severity:** High

**API:** POST /booking

**Request:**

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

**Expected:**
API should reject the invalid date.

**Actual:**
API returned `200` and stored the check-in date as `0NaN-aN-aN`.

---

## BUG-04: Missing firstname returns HTTP 500

**Severity:** High

**API:** POST /booking

**Request:**

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

**Expected:**
API should return a proper client validation error.

**Actual:**
API returned `500 Internal Server Error`.

---

## BUG-05: Empty request returns HTTP 500

**Severity:** High

**API:** POST /booking

**Request:**

```json
{}
```

**Expected:**
API should return a proper validation error.

**Actual:**
API returned `500 Internal Server Error`.

---

## BUG-06: Wrong data type results in null total price

**Severity:** Medium

**API:** POST /booking

**Request:**

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

**Expected:**
API should reject the request because `totalprice` should be a number.

**Actual:**
API returned `200` and the response contained:

```json
"totalprice": null
```

---

## Boundary Observation: Zero total price

**API:** POST /booking

**Actual:**
API accepted `totalprice: 0` and returned `200`.

This is documented as a boundary observation because the assignment does not explicitly state whether zero should be considered invalid.
