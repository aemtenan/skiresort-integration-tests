# Integration Tests for Ski Resort Booking System REST API

## Introduction
In this project, we implement the integration tests for the Ski Resort Booking System REST API

### Tools Used:
* Environment: OpenJDK 21
* Test Framework: JUnit 5
* REST CLient: REST Assured

### Instructions:
* To run tests, in IntelliJ, execute the ResortAPITests class.
![alt text](https://github.com/aemtenan/skiresort-integration-tests/blob/main/src/main/resources/static/tests.png?raw=true)


## Test Project Architecture
We initially write tests for the Resort REST API which allows an admin to add/update/retrieve and delete a ski resort.
The test project is organized as follows:
* client package: contains the ResortAPIClient which makes REST calls to the Resort API.
* data package: contains test and configuration data
* model package: contains the models to represent the test payload
* test package: contains the positive and negative tests

We also ensure there is no state between tests, and each test creates and deletes its own test objects.

### Positive Tests
* CRUDTest() - Creates a Resort, retrieves the Resort, Updates the Resort, retrieves updated Resort, deletes the Resort.
### Negative Tests
* invalidHeaders() - POST call with invalid ContentType.TEXT, verifies 415 error status code
* invalidPayload() - POST call with invalid payload parameter, verifies null value in relevant response field
* invalidMethod() - Makes a PUT call to the URL specified for a POST call, verifies 405 error status code
* invalidURL() - Makes a GET call to an invalid URL, verifies 404 error status code
### TODO: Security Focused Tests
* Authentication and Authorization failure scenarios
* Malformed Input: Validate length/range/format/type of input parameters
* SQL/Malicious Injection Attack testing
### TODO: Performance Focused Tests
* Rate Limits: Test functionality in X-Rate-Limit-Limit, X-Rate-Limit-Remaining and X-Rate-Limit-Reset headers
* Paging: Test paging settings
