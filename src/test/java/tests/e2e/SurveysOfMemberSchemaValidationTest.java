package tests.e2e;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

public class SurveysOfMemberSchemaValidationTest {
    @Test
    public void testSurveyOfMembersResponseValidation() {
        for (int memberId = 1; memberId <= 300; memberId++) {
            ApiCallHelper.get("/members/" + memberId).then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/surveys-of-member-schema.json"));
        }
    }
}