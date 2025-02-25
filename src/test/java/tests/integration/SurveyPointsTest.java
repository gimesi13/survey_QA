package tests.integration;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.SurveyPointsApi;

@ExtendWith(AllureJunit5.class)
@Tag("integration")
@Epic("API Tests")
@Feature("Integration")
@Story("Survey Points")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Integration - Survey Points")
public class SurveyPointsTest extends SurveyPointsApi {

    private static final int FINAL_MEMBER_ID = 100;
    private static final int STEP = 3;

    @Test
    @DisplayName("Verify Survey IDs")
    @Description("Verifies the survey IDs in the survey points response")
    void testSurveyIds() {
        testSurveyPoints("Survey Id", FINAL_MEMBER_ID, STEP);
    }

    @Test
    @DisplayName("Verify Survey Names")
    @Description("Verifies the survey names in the survey points response")
    void testSurveyNames() {
        testSurveyPoints("Name", FINAL_MEMBER_ID, STEP);
    }

    @Test
    @DisplayName("Verify Survey Expected Completes")
    @Description("Verifies the expected completes in the survey points response")
    void testSurveyExpectedCompletes() {
        testSurveyPoints("Expected completes", FINAL_MEMBER_ID, STEP);
    }

    @Test
    @DisplayName("Verify Survey Completion Points")
    @Description("Verifies the completion points in the survey points response")
    void testSurveyCompletionPoints() {
        testSurveyPoints("Completion points", FINAL_MEMBER_ID, STEP);
    }

    @Test
    @DisplayName("Verify Survey Filtered Points")
    @Description("Verifies the filtered points in the survey points response")
    void testSurveyFilteredPoints() {
        testSurveyPoints("Filtered points", FINAL_MEMBER_ID, STEP);
    }

    @Test
    @DisplayName("Validate Survey Points")
    @Description("Validates the points data in the survey points response")
    void testSurveyPointsValidation() {
        validatePoints(FINAL_MEMBER_ID, STEP);
    }
}