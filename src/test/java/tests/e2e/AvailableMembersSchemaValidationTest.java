package tests.e2e;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

public class AvailableMembersSchemaValidationTest {

    @Test
    public void testAvailableResponseValidation() {
        for (int surveyId = 1; surveyId <= 100; surveyId++) {
            ApiCallHelper.get("/surveys/" + surveyId + "/members/not-invited").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/available-schema.json"));
        }
    }
}