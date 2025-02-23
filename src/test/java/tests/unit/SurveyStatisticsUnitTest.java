package tests.unit;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

@ExtendWith(AllureJunit5.class)
@Tag("unit")
@Epic("API Tests")
@Feature("Unit")
@Story("Survey Statistics)")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Survey Statistics")
public class SurveyStatisticsUnitTest {

    @Test
    @DisplayName("Verify Statistics for Survey 1")
    @Description("Verifies the statistics data for survey with ID 1")
    void testSurvey1Unit() {
        Allure.step("Sending API request to fetch survey statistics");
        Response response = ApiCallHelper.get("/surveys/statistics");

        try {
            Allure.step("Validating response for Survey 1");
            response.then()
                    .assertThat()
                    .body("surveyName", hasItem("Survey 01"))
                    .body("[0].surveyId", equalTo(1))
                    .body("[0].numberOfCompletes", equalTo(21))
                    .body("[0].numberOfFiltered", equalTo(8))
                    .body("[0].numberOfRejected", equalTo(4))
                    .body("[0].avgLengthOfSurvey", equalTo(13));
        } catch (AssertionError e) {
            Allure.addAttachment("Survey 1 API Response", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Statistics for Survey 100")
    @Description("Verifies the statistics data for survey with ID 100")
    void testSurvey100Unit() {
        Allure.step("Sending API request to fetch survey statistics");
        Response response = ApiCallHelper.get("/surveys/statistics");

        try {
            Allure.step("Validating response for Survey 100");
            response.then()
                    .assertThat()
                    .body("surveyName", hasItem("Survey 100"))
                    .body("[99].surveyId", equalTo(100))
                    .body("[99].numberOfCompletes", equalTo(24))
                    .body("[99].numberOfFiltered", equalTo(3))
                    .body("[99].numberOfRejected", equalTo(5))
                    .body("[99].avgLengthOfSurvey", equalTo(15));
        } catch (AssertionError e) {
            Allure.addAttachment("Survey 100 API Response", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }
}
