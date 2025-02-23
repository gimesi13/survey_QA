package tests.e2e;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

@ExtendWith(AllureJunit5.class)
@Tag("e2e")
@Epic("API Tests")
@Feature("E2E")
@Story("Survey Statistics Schema Validatio")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("E2E - Survey Statistics Schema Validation")
public class StatisticsSchemaValidationTest {

    @Test
    @DisplayName("Validate Survey Statistics Response Schema")
    @Description("Validates the response schema for survey statistics from the API")
    void testSurveyStatisticsResponseValidation() {
        ApiCallHelper.get("/surveys/statistics").then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/statistics-schema.json"));
    }
}
