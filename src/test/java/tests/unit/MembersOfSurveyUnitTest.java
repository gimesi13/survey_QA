package tests.unit;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("unit")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Members of Survey")
public class MembersOfSurveyUnitTest {

    @Test
    @DisplayName("Verify Completed Survey '1'")
    @Description("Verifies that some members completed survey '1'")
    void testMembersOfSurveyCompleted1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=COMPLETED").then()
                .assertThat()
                .body("id", hasItem(2))
                .body("id", not(hasItem(1)));
    }

    @Test
    @DisplayName("Verify Rejected Survey '1'")
    @Description("Verifies that some members were rejected from survey '1'")
    void testMembersOfSurveyRejected1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=REJECTED").then()
                .assertThat()
                .body("id", hasItem(38))
                .body("id", not(hasItem(3)));
    }

    @Test
    @DisplayName("Verify Not Asked Survey '1'")
    @Description("Verifies that no members were not asked for survey '1'")
    void testMembersOfSurveyNotAsked1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=NOT_ASKED").then()
                .assertThat()
                .body("$", hasSize(0));
    }

    @Test
    @DisplayName("Verify Filtered Survey '1'")
    @Description("Verifies that some members were filtered in survey '1'")
    void testMembersOfSurveyFiltered1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=FILTERED").then()
                .assertThat()
                .body("id", hasItem(35))
                .body("id", not(hasItem(4)));
    }
}