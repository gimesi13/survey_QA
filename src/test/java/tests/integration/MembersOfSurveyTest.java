package tests.integration;

import api.MembersOfSurveyApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MembersOfSurveyTest {

    private static MembersOfSurveyApi membersOfSurveyApi;

    @BeforeAll
    static void setup() {
        membersOfSurveyApi = new MembersOfSurveyApi();
    }

    @Test
    public void testSurveyMembersForNotAskedStatus() {
        membersOfSurveyApi.validateSurveyMembersForStatus("NOT_ASKED");
    }

    @Test
    public void testSurveyMembersForRejectedStatus() {
        membersOfSurveyApi.validateSurveyMembersForStatus("REJECTED");
    }

    @Test
    public void testSurveyMembersForFilteredStatus() {
        membersOfSurveyApi.validateSurveyMembersForStatus("FILTERED");
    }

    @Test
    public void testSurveyMembersForCompletedStatus() {
        membersOfSurveyApi.validateSurveyMembersForStatus("COMPLETED");
    }
}