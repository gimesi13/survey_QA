This project provides automated tests for API endpoints related to managing and querying survey participation data stored in CSV files. The tests ensure API functionality, correctness, and compliance with business rules.

Project Structure:

survey_QA/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── api/            # Test logic for each endpoint
│   │   │   ├── config/         # Configuration class
│   ├── test/
│   │   ├── java/
│   │   │   ├── resources/
│   │   │   │   ├── csv/        # CSV files
│   │   │   │   ├── schemas/    # JSON schemas for API response validation
│   │   │   ├── tests/
│   │   │   │   ├── e2e/        # Schema validation
│   │   │   │   ├── integration/# Integration tests
│   │   │   │   ├── unit/       # Unit tests
│   │   │   ├── utils/          # Helper classes (e.g., assertions, API calls)
├── pom.xml                     # Maven project configuration
├── README.md                   
├── dynata-surveys-0.0.1-SNAPSHOT.jar # Application JAR file

Key Dependencies:
1. JUnit 5

   Purpose: Framework for writing and executing tests.
   Why It’s Needed: Supports writing concise unit, integration, and end-to-end tests with modern features like parameterized tests and assertions.
2. RestAssured

   Purpose: Simplifies REST API testing by providing an intuitive DSL for making API calls and validating responses.
   Why It’s Needed: Enables easy assertion of API responses, particularly useful for JSON schema validation and HTTP response testing.
3. SLF4J

   Purpose: Logging abstraction that enables switching between different logging frameworks without changing application code.
   Why It’s Needed: Ensures flexible and consistent logging for debugging and tracking test executions.
4. Logback

   Purpose: A robust and widely used logging framework, compatible with SLF4J.
   Why It’s Needed: Provides reliable and configurable logging, crucial for debugging test cases and tracking API interactions.
5. Apache Commons CSV

   Purpose: Library for reading and writing CSV files.
   Why It’s Needed: Handles survey-related data stored in CSV files, allowing seamless parsing for tests and APIs.


Setup and Usage:

Running the Application:
bash
java -jar dynata-surveys-0.0.1-SNAPSHOT.jar

API Documentation:
Swagger UI:
http://localhost:8080/swagger-ui/index.html

Running Tests:
bash
mvn test