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
@DisplayName("E2E - Surveys of Member Schema Validation")
public class SurveysOfMemberSchemaValidationTest {

    private static final int FINAL_MEMBER_ID = 300;
    private static final int STEP = 5;

    @Test
    @DisplayName("Validate Surveys of Member Response Schema")
    @Description("Validates the response schema for surveys of a member from the API")
    void testSurveyOfMembersResponseValidation() {
        for (int memberId = 1; memberId <= FINAL_MEMBER_ID; memberId += STEP) {
            ApiCallHelper.get("/members/" + memberId).then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/surveys-of-member-schema.json"));
        }
    }
}