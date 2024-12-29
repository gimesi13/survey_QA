package tests.e2e;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

public class StatisticsSchemaValidationTest {

    @Test
    public void testSurveyStatisticsResponseValidation() {
        ApiCallHelper.get("/surveys/statistics").then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/statistics-schema.json"));
    }
}