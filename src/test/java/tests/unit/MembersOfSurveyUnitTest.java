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
@Feature("API")
@Story("Unit")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Members of Survey")
public class MembersOfSurveyUnitTest {

    @Test
    @DisplayName("Verify Completed Survey '1'")
    @Description("Verifies that some members completed survey '1'")
    void testMembersOfSurveyCompleted1Unit() {
        Allure.step("Sending API request to fetch members with status COMPLETED for Survey 1");
        Response response = ApiCallHelper.get("/surveys/1/members?status=COMPLETED");

        try {
            Allure.step("Validating response for COMPLETED members of Survey 1");
            response.then()
                    .assertThat()
                    .body("id", hasItem(2))
                    .body("id", not(hasItem(1)));
        } catch (AssertionError e) {
            Allure.addAttachment("Members of Survey Response - COMPLETED", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Rejected Survey '1'")
    @Description("Verifies that some members were rejected from survey '1'")
    void testMembersOfSurveyRejected1Unit() {
        Allure.step("Sending API request to fetch members with status REJECTED for Survey 1");
        Response response = ApiCallHelper.get("/surveys/1/members?status=REJECTED");

        try {
            Allure.step("Validating response for REJECTED members of Survey 1");
            response.then()
                    .assertThat()
                    .body("id", hasItem(38))
                    .body("id", not(hasItem(3)));
        } catch (AssertionError e) {
            Allure.addAttachment("Members of Survey Response - REJECTED", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Not Asked Survey '1'")
    @Description("Verifies that no members were not asked for survey '1'")
    void testMembersOfSurveyNotAsked1Unit() {
        Allure.step("Sending API request to fetch members with status NOT_ASKED for Survey 1");
        Response response = ApiCallHelper.get("/surveys/1/members?status=NOT_ASKED");

        try {
            Allure.step("Validating response for NOT_ASKED members of Survey 1");
            response.then()
                    .assertThat()
                    .body("$", hasSize(0));
        } catch (AssertionError e) {
            Allure.addAttachment("Members of Survey Response - NOT_ASKED", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Filtered Survey '1'")
    @Description("Verifies that some members were filtered in survey '1'")
    void testMembersOfSurveyFiltered1Unit() {
        Allure.step("Sending API request to fetch members with status FILTERED for Survey 1");
        Response response = ApiCallHelper.get("/surveys/1/members?status=FILTERED");

        try {
            Allure.step("Validating response for FILTERED members of Survey 1");
            response.then()
                    .assertThat()
                    .body("id", hasItem(35))
                    .body("id", not(hasItem(4)));
        } catch (AssertionError e) {
            Allure.addAttachment("Members of Survey Response - FILTERED", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }
}
