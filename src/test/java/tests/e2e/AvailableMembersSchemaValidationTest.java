package tests.e2e;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("e2e")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("E2E - Available Members Schema Validation")
public class AvailableMembersSchemaValidationTest {

    private static final int FINAL_SURVEY_ID = 100;
    private static final int STEP = 3;

    @Test
    @DisplayName("Validate Available Members Response Schema")
    @Description("Validates the response schema for available members not invited to a survey")
    void testAvailableResponseValidation() {
        for (int surveyId = 1; surveyId <= FINAL_SURVEY_ID; surveyId += STEP) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members/not-invited").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/available-schema.json"));
        }
    }
}