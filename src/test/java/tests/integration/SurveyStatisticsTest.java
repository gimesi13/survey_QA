package tests.integration;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.SurveyStatApi;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("integration")
@Epic("API")
@Feature("Unit")
@Story("Story annotation (Integration - Survey Statistics)")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Integration - Survey Statistics")
public class SurveyStatisticsTest extends SurveyStatApi {

    @Test
    @DisplayName("Verify Survey IDs")
    @Description("Verifies the survey IDs in the survey statistics response")
    void testSurveyIds() {
        testSurveyStatistic("Survey Id");
    }

    @Test
    @DisplayName("Verify Number of Completes")
    @Description("Verifies the number of completes in the survey statistics response")
    void testNumberOfCompletes() {
        testSurveyStatistic("Number of Completes");
    }

    @Test
    @DisplayName("Verify Number of Filtered")
    @Description("Verifies the number of filtered responses in the survey statistics response")
    void testNumberOfFiltered() {
        testSurveyStatistic("Number of Filtered");
    }

    @Test
    @DisplayName("Verify Number of Rejected")
    @Description("Verifies the number of rejected responses in the survey statistics response")
    void testNumberOfRejected() {
        testSurveyStatistic("Number of Rejected");
    }

    @Test
    @DisplayName("Verify Average Length of Survey")
    @Description("Verifies the average length of the survey in the survey statistics response")
    void testAverageLength() {
        testSurveyStatistic("Average Length");
    }
}