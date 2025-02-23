package tests.integration;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.SurveyPointsApi;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("integration")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Integration - Survey Points")
public class SurveyPointsTest extends SurveyPointsApi {

    @Test
    @DisplayName("Verify Survey IDs")
    @Description("Verifies the survey IDs in the survey points response")
    void testSurveyIds() {
        testSurveyPoints("Survey Id");
    }

    @Test
    @DisplayName("Verify Survey Names")
    @Description("Verifies the survey names in the survey points response")
    void testSurveyNames() {
        testSurveyPoints("Name");
    }

    @Test
    @DisplayName("Verify Survey Expected Completes")
    @Description("Verifies the expected completes in the survey points response")
    void testSurveyExpectedCompletes() {
        testSurveyPoints("Expected completes");
    }

    @Test
    @DisplayName("Verify Survey Completion Points")
    @Description("Verifies the completion points in the survey points response")
    void testSurveyCompletionPoints() {
        testSurveyPoints("Completion points");
    }

    @Test
    @DisplayName("Verify Survey Filtered Points")
    @Description("Verifies the filtered points in the survey points response")
    void testSurveyFilteredPoints() {
        testSurveyPoints("Filtered points");
    }

    @Test
    @DisplayName("Validate Survey Points")
    @Description("Validates the points data in the survey points response")
    void testSurveyPointsValidation() {
        validatePoints();
    }
}