package tests.unit;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("unit")
@Epic("API")
@Feature("Unit")
@Story("Story annotation (Unit - Survey Points)")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Survey Points")
public class SurveyPointsUnitTest {

    @Test
    @DisplayName("Verify Points for Member 1")
    @Description("Verifies the survey points data for member with ID 1")
    void testPoints1Unit() {
        Allure.step("Sending API request to fetch points for Member 1");
        Response response = ApiCallHelper.get("/members/1/points");

        try {
            Allure.step("Validating response for Member 1");
            response.then()
                    .assertThat()
                    .body("survey.name", hasItems("Survey 16", "Survey 25", "Survey 45", "Survey 79", "Survey 82", "Survey 95"))
                    .body("[0].survey.id", equalTo(16))
                    .body("[0].survey.expectedCompletes", equalTo(90))
                    .body("[0].survey.completionPoints", equalTo(45))
                    .body("[0].survey.filteredPoints", equalTo(3))
                    .body("[0].point", equalTo(45))

                    .body("[5].survey.id", equalTo(95))
                    .body("[5].survey.expectedCompletes", equalTo(80))
                    .body("[5].survey.completionPoints", equalTo(15))
                    .body("[5].survey.filteredPoints", equalTo(5))
                    .body("[5].point", equalTo(0));
        } catch (AssertionError e) {
            Allure.addAttachment("Survey Points Response - Member 1", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Points for Member 300")
    @Description("Verifies the survey points data for member with ID 300")
    void testPoints300Unit() {
        Allure.step("Sending API request to fetch points for Member 300");
        Response response = ApiCallHelper.get("/members/300/points");

        try {
            Allure.step("Validating response for Member 300");
            response.then()
                    .assertThat()
                    .body("survey.name", hasItems("Survey 05", "Survey 12", "Survey 24", "Survey 46", "Survey 53"))
                    .body("[0].survey.id", equalTo(5))
                    .body("[0].survey.expectedCompletes", equalTo(20))
                    .body("[0].survey.completionPoints", equalTo(35))
                    .body("[0].survey.filteredPoints", equalTo(4))
                    .body("[0].point", equalTo(35))

                    .body("[4].survey.id", equalTo(53))
                    .body("[4].survey.expectedCompletes", equalTo(70))
                    .body("[4].survey.completionPoints", equalTo(20))
                    .body("[4].survey.filteredPoints", equalTo(5))
                    .body("[4].point", equalTo(20));
        } catch (AssertionError e) {
            Allure.addAttachment("Survey Points Response - Member 300", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }
}
