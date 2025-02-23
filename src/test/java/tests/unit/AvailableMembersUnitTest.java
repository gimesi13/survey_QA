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
@DisplayName("Unit - Available Members")
public class AvailableMembersUnitTest {

    @Test
    @DisplayName("Verify Member 1 is Available")
    @Description("Verifies that member with ID 1 is available in the not-invited list for survey 1")
    void testAvailable1Unit() {
        Allure.step("Sending API request to fetch available members for Survey 1");
        Response response = ApiCallHelper.get("/surveys/1/members/not-invited");

        try {
            Allure.step("Validating response for available members in Survey 1");
            response.then()
                    .assertThat()
                    .body("id", hasItem(1))
                    .body("id", not(hasItem(290)));
        } catch (AssertionError e) {
            Allure.addAttachment("Available Members Response - Survey 1", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }

    @Test
    @DisplayName("Verify Member 50 is Available")
    @Description("Verifies that member with ID 50 is available in the not-invited list for survey 1")
    void testAvailable50Unit() {
        Allure.step("Sending API request to fetch available members for Survey 1");
        Response response = ApiCallHelper.get("/surveys/1/members/not-invited");

        try {
            Allure.step("Validating response for available members in Survey 1");
            response.then()
                    .assertThat()
                    .body("id", hasItem(286))
                    .body("id", not(hasItem(287)));
        } catch (AssertionError e) {
            Allure.addAttachment("Available Members Response - Survey 1", "text/plain", response.body().asPrettyString());
            throw e;
        }
    }
}
