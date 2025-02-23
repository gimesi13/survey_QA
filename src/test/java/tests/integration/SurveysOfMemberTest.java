package tests.integration;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import api.SurveysOfMemberApi;

@ExtendWith(AllureJunit5.class)
@Tag("api")
@Tag("integration")
@Feature("API")
@Story("Integration")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Integration - Surveys of Member")
public class SurveysOfMemberTest extends SurveysOfMemberApi {

    @Test
    @DisplayName("Validate Member Survey Data")
    @Description("Verifies the survey data for a specific member")
    void testValidateMemberSurveyData() {
        validateMemberSurveyData();
    }
}