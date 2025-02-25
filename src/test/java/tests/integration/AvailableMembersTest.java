package tests.integration;

import api.AvailableMembersApi;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureJunit5.class)
@Tag("integration")
@Epic("API Tests")
@Feature("Integration")
@Story("Available Members")
@Severity(SeverityLevel.NORMAL)
@DisplayName("Integration - Available Members")
public class AvailableMembersTest extends AvailableMembersApi {

    private static final int FINAL_SURVEY_ID = 100;
    private static final int STEP = 3;

    @Test
    @DisplayName("Verify Available Member IDs")
    @Description("Verifies the available member IDs in the response")
    void testAvailableIds() {
        validateAvailableMembers("id", FINAL_SURVEY_ID, STEP);
    }

    @Test
    @DisplayName("Verify Available Member Names")
    @Description("Verifies the available member names in the response")
    void testAvailableNames() {
        validateAvailableMembers("name", FINAL_SURVEY_ID, STEP);
    }

    @Test
    @DisplayName("Verify Available Member Emails")
    @Description("Verifies the available member emails in the response")
    void testAvailableEmails() {
        validateAvailableMembers("email", FINAL_SURVEY_ID, STEP);
    }

    @Test
    @DisplayName("Verify Available Member Statuses")
    @Description("Verifies the available member statuses in the response")
    void testAvailableStatuses() {
        validateAvailableMembers("status", FINAL_SURVEY_ID, STEP);
    }
}