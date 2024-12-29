
# Survey Participation API - Test Automation Guide

This project involves testing the API endpoints related to survey participation. The API allows you to fetch information about survey respondents, survey statistics, and their participation details. You are tasked with writing tests for these API endpoints to ensure their functionality and reliability. 

## Prerequisites

Java 11 or higher installed on your machine.

## Build and Run the Application

Once you have cloned the repository, build the application using the following command:

java -jar dynata-surveys-0.0.1-SNAPSHOT.jar

This command will start the application. By default, the application will run on localhost:8080.

## Testing Guidelines

To test the API endpoints, ensure that the following steps are followed:

1. Set up the test environment

   * Ensure that the application is running locally on localhost:8080 as described above.

2. Writing Tests

   * Build a simple test automation framework for your test cases.
   * Write automated tests to verify the correct behavior of the endpoints.

## Data Format

The application works with the following CSV files for storing member and survey data:

* Members.csv: Contains a list of all potential members for surveys.
* Surveys.csv: Contains details about the surveys available for participation.
* Participation.csv: Contains details about which members participated in which surveys.
* Statuses.csv: Contains all the possible statuses for a survey participation (Not asked, Rejected, Filtered, Completed).

## Swagger Documentation

Once the application is running, you can view the Swagger UI for API documentation at the following URL: http://localhost:8080/swagger-ui/index.html

## See more 

For further details see description.txt

