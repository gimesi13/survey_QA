package tests.unit;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import api.BaseApi;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureJunit5.class)
@Feature("Unit tests")
@Story("Story annotation")
@Tag("api")
@Tag("unit")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Unit - Survey points")
public class ResponseUnitTest extends BaseApi{

    //SURVEY POINTS
    @Test
    void testSurveyPointsStatusSuccess() {
        statusTest("/members/1/points", 200);
        statusTest("/members/300/points", 200);
    }

    @Test
    void testSurveyPointsStatusFail() {
        statusTest("/members/test/points", 400);
        statusTest("members/1/point", 404);
        statusTest("/members/0/points", 500);
        statusTest("/members/999/points", 500);
    }

    //SURVEY STATISTICS
    @Test
    void testSurveyStatisticsStatusSuccess() {
        statusTest("/surveys/statistics", 200);
    }

    @Test
    void testSurveyStatisticsStatusFail() {
        statusTest("/surveys/statistics/123", 404);
    }

    //AVAILABLE MEMBERS
    @Test
    void testAvailableMembersStatusSuccess() {
        statusTest("/surveys/1/members/not-invited", 200);
        statusTest("/surveys/100/members/not-invited", 200);
    }

    @Test
    void testAvailableMembersStatusFail() {
        statusTest("/surveys/test/members/not-invited", 400);
        statusTest("/surveys/1/members/not-invited/123", 404);
        statusTest("/surveys/0/members/not-invited", 500);
        statusTest("/surveys/101/members/not-invited", 500);
    }

    //SURVEYS OF MEMBER
    @Test
    void testSurveysOfMemberStatusSuccess() {
        statusTest("/members/1", 200);
        statusTest("/members/300", 200);
    }

    @Test
    void testSurveysOfMemberStatusFail() {
        statusTest("/members/test", 400);
        statusTest("/members/1/test", 404);
        statusTest("/members/0", 500);
        statusTest("/members/301", 500);
    }

    //MEMBERS OF SURVEY
    @Test
    void testMembersOfSurveyStatusSuccess() {
        statusTest("/surveys/1/members?status=NOT_ASKED", 200);
        statusTest("/surveys/25/members?status=REJECTED", 200);
        statusTest("/surveys/50/members?status=FILTERED", 200);
        statusTest("/surveys/100/members?status=COMPLETED", 200);
    }

    @Test
    void testMembersOfSurveyStatusFail() {
        statusTest("/surveys/1/members?status=TEST", 400);
        statusTest("/surveys/1/members/123?status=NOT_ASKED", 404);
        statusTest("/surveys/0/members?status=FILTERED", 500);
        statusTest("/surveys/101/members?status=COMPLETED", 500);
    }
}