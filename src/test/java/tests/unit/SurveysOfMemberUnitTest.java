package tests.unit;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

@ExtendWith(AllureJunit5.class)
@Tag("unit")
@Epic("API Tests")
@Feature("Unit")
@Story("Surveys of Member")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Surveys of Member")
public class SurveysOfMemberUnitTest {

    @Test
    @DisplayName("Verify Surveys for Member 10")
    @Description("Verifies the survey data for member with ID 10")
    void testSurveysOfMember10Unit() {
        Allure.step("Sending API request to fetch surveys for Member 10");
        Response response = ApiCallHelper.get("/members/10");

        try {
            Allure.step("Validating response for Member 10");
            response.then()
                    .assertThat()
                    .body("[0].id", equalTo(2))
                    .body("[0].expectedCompletes", equalTo(70))
                    .body("[0].completionPoints", equalTo(15))
                    .body("[0].filteredPoints", equalTo(4))

                    .body("[5].id", equalTo(33))
                    .body("[5].expectedCompletes", equalTo(20))
                    .body("[5].completionPoints", equalTo(25))
                    .body("[5].filteredPoints", equalTo(5));
        } catch (AssertionError e) {
            Allure.addAttachment("Survey Response - Member 10", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Surveys for Member 299")
    @Description("Verifies the survey data for member with ID 299")
    void testSurveysOfMember299Unit() {
        Allure.step("Sending API request to fetch surveys for Member 299");
        Response response = ApiCallHelper.get("/members/299");

        try {
            Allure.step("Validating response for Member 299");
            response.then()
                    .assertThat()
                    .body("[0].id", equalTo(3))
                    .body("[0].expectedCompletes", equalTo(100))
                    .body("[0].completionPoints", equalTo(10))
                    .body("[0].filteredPoints", equalTo(2))

                    .body("[3].id", equalTo(55))
                    .body("[3].expectedCompletes", equalTo(70))
                    .body("[3].completionPoints", equalTo(35))
                    .body("[3].filteredPoints", equalTo(1));
        } catch (AssertionError e) {
            Allure.addAttachment("Survey Response - Member 299", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }
}