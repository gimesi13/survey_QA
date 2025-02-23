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
@DisplayName("E2E - Members of Survey Schema Validation")
public class MembersOfSurveySchemaValidationTest {

    private static final int FINAL_SURVEY_ID = 100;
    private static final int STEP = 3;

    @Test
    @DisplayName("Validate Members of Survey Response Schema - NOT_ASKED")
    @Description("Validates the response schema for members with status NOT_ASKED in survey API")
    void testMembersOfSurveyResponseValidationNotAsked() {
        for (int surveyId = 1; surveyId <= FINAL_SURVEY_ID; surveyId += STEP) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=NOT_ASKED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }

    @Test
    @DisplayName("Validate Members of Survey Response Schema - REJECTED")
    @Description("Validates the response schema for members with status REJECTED in survey API")
    void testMembersOfSurveyResponseValidationRejected() {
        for (int surveyId = 1; surveyId <= FINAL_SURVEY_ID; surveyId += STEP) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=REJECTED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }

    @Test
    @DisplayName("Validate Members of Survey Response Schema - FILTERED")
    @Description("Validates the response schema for members with status FILTERED in survey API")
    void testMembersOfSurveyResponseValidationFiltered() {
        for (int surveyId = 1; surveyId <= FINAL_SURVEY_ID; surveyId += STEP) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=FILTERED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }

    @Test
    @DisplayName("Validate Members of Survey Response Schema - COMPLETED")
    @Description("Validates the response schema for members with status COMPLETED in survey API")
    void testMembersOfSurveyResponseValidationCompleted() {
        for (int surveyId = 1; surveyId <= FINAL_SURVEY_ID; surveyId += STEP) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=COMPLETED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }
}
