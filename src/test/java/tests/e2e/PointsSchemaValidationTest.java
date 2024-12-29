package tests.e2e;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

public class PointsSchemaValidationTest {

    @Test
    public void testPointsResponseValidation() {
        for (int memberId = 1; memberId <= 300; memberId++) {
            ApiCallHelper.get("/members/" + memberId + "/points").then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/points-schema.json"));
        }
    }
}