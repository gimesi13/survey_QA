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
@Story("Points Schema Validation")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("E2E - Points Schema Validation")
public class PointsSchemaValidationTest {

    private static final int FINAL_MEMBER_ID = 300;
    private static final int STEP = 5;

    @Test
    @DisplayName("Validate Points Response Schema")
    @Description("Validates the response schema for member points from the API")
    void testPointsResponseValidation() {
        for (int memberId = 1; memberId <= FINAL_MEMBER_ID; memberId += STEP) {
            ApiCallHelper.get("/members/" + memberId + "/points").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/points-schema.json"));
        }
    }
}
