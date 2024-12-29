package tests.e2e;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

public class MembersOfSurveySchemaValidationTest {

    @Test
    public void testMembersOfSurveyResponseValidationNotAsked() {
        for (int surveyId = 1; surveyId <= 100; surveyId++) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=NOT_ASKED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }
    @Test
    public void testMembersOfSurveyResponseValidationRejected() {
        for (int surveyId = 1; surveyId <= 100; surveyId++) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=REJECTED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }
    @Test
    public void testMembersOfSurveyResponseValidationFiltered() {
        for (int surveyId = 1; surveyId <= 100; surveyId++) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=FILTERED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }
    @Test
    public void testMembersOfSurveyResponseValidationCOMPLETED() {
        for (int surveyId = 1; surveyId <= 100; surveyId++) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members?status=COMPLETED").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/members-of-survey-schema.json"));
        }
    }
}