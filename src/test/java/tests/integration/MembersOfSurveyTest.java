package tests.integration;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.MembersOfSurveyApi;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("integration")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Integration - Members of Survey")
public class MembersOfSurveyTest extends MembersOfSurveyApi {

    @Test
    @DisplayName("Verify Members with NOT_ASKED Status")
    @Description("Validates the members with NOT_ASKED status in the survey")
    void testSurveyMembersForNotAskedStatus() {
        validateSurveyMembersForStatus("NOT_ASKED");
    }

    @Test
    @DisplayName("Verify Members with REJECTED Status")
    @Description("Validates the members with REJECTED status in the survey")
    void testSurveyMembersForRejectedStatus() {
        validateSurveyMembersForStatus("REJECTED");
    }

    @Test
    @DisplayName("Verify Members with FILTERED Status")
    @Description("Validates the members with FILTERED status in the survey")
    void testSurveyMembersForFilteredStatus() {
        validateSurveyMembersForStatus("FILTERED");
    }

    @Test
    @DisplayName("Verify Members with COMPLETED Status")
    @Description("Validates the members with COMPLETED status in the survey")
    void testSurveyMembersForCompletedStatus() {
        validateSurveyMembersForStatus("COMPLETED");
    }
}