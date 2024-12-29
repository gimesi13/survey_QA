package tests.integration;

import api.SurveyPointsApi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SurveyPointsTest {

    private static SurveyPointsApi surveyPointsApi;

    @BeforeAll
    static void setup() {
        surveyPointsApi = new SurveyPointsApi();
    }

    @Test
    void testSurveyIds() {
        surveyPointsApi.testSurveyPoints("Survey Id", "id");
    }

    @Test
    void testSurveyNames() {
        surveyPointsApi.testSurveyPoints("Name", "name");
    }

    @Test
    void testSurveyExpectedCompletes() {
        surveyPointsApi.testSurveyPoints("Expected completes", "expectedCompletes");
    }

    @Test
    void testSurveyCompletionPoints() {
        surveyPointsApi.testSurveyPoints("Completion points", "completionPoints");
    }

    @Test
    void testSurveyFilteredPoints() {
        surveyPointsApi.testSurveyPoints("Filtered points", "filteredPoints");
    }

    @Test
    void testSurveyPointsValidation() {
        surveyPointsApi.validatePoints();
    }
}