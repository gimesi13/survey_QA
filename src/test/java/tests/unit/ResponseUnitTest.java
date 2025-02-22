package tests.unit;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.BaseApi;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("unit")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Basic Responses")
public class ResponseUnitTest extends BaseApi {

    @Test
    @DisplayName("Verify Survey Points Status Success")
    @Description("Verifies successful status codes for survey points endpoints")
    void testSurveyPointsStatusSuccess() {
        statusTest("/members/1/points", 200);
        statusTest("/members/300/points", 200);
    }

    @Test
    @DisplayName("Verify Survey Points Status Fail")
    @Description("Verifies failure status codes for survey points endpoints")
    void testSurveyPointsStatusFail() {
        statusTest("/members/test/points", 400);
        statusTest("members/1/point", 404);
        statusTest("/members/0/points", 500);
        statusTest("/members/999/points", 500);
    }

    @Test
    @DisplayName("Verify Survey Statistics Status Success")
    @Description("Verifies successful status codes for survey statistics endpoints")
    void testSurveyStatisticsStatusSuccess() {
        statusTest("/surveys/statistics", 200);
    }

    @Test
    @DisplayName("Verify Survey Statistics Status Fail")
    @Description("Verifies failure status codes for survey statistics endpoints")
    void testSurveyStatisticsStatusFail() {
        statusTest("/surveys/statistics/123", 404);
    }

    @Test
    @DisplayName("Verify Available Members Status Success")
    @Description("Verifies successful status codes for available members endpoints")
    void testAvailableMembersStatusSuccess() {
        statusTest("/surveys/1/members/not-invited", 200);
        statusTest("/surveys/100/members/not-invited", 200);
    }

    @Test
    @DisplayName("Verify Available Members Status Fail")
    @Description("Verifies failure status codes for available members endpoints")
    void testAvailableMembersStatusFail() {
        statusTest("/surveys/test/members/not-invited", 400);
        statusTest("/surveys/1/members/not-invited/123", 404);
        statusTest("/surveys/0/members/not-invited", 500);
        statusTest("/surveys/101/members/not-invited", 500);
    }

    @Test
    @DisplayName("Verify Surveys of Member Status Success")
    @Description("Verifies successful status codes for surveys of member endpoints")
    void testSurveysOfMemberStatusSuccess() {
        statusTest("/members/1", 200);
        statusTest("/members/300", 200);
    }

    @Test
    @DisplayName("Verify Surveys of Member Status Fail")
    @Description("Verifies failure status codes for surveys of member endpoints")
    void testSurveysOfMemberStatusFail() {
        statusTest("/members/test", 400);
        statusTest("/members/1/test", 404);
        statusTest("/members/0", 500);
        statusTest("/members/301", 500);
    }

    @Test
    @DisplayName("Verify Members of Survey Status Success")
    @Description("Verifies successful status codes for members of survey endpoints")
    void testMembersOfSurveyStatusSuccess() {
        statusTest("/surveys/1/members?status=NOT_ASKED", 200);
        statusTest("/surveys/25/members?status=REJECTED", 200);
        statusTest("/surveys/50/members?status=FILTERED", 200);
        statusTest("/surveys/100/members?status=COMPLETED", 200);
    }

    @Test
    @DisplayName("Verify Members of Survey Status Fail")
    @Description("Verifies failure status codes for members of survey endpoints")
    void testMembersOfSurveyStatusFail() {
        statusTest("/surveys/1/members?status=TEST", 400);
        statusTest("/surveys/1/members/123?status=NOT_ASKED", 404);
        statusTest("/surveys/0/members?status=FILTERED", 500);
        statusTest("/surveys/101/members?status=COMPLETED", 500);
    }
}