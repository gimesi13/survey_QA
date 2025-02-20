//package tests.integration;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import api.SurveyStatApi;
//
//class SurveyStatisticsTest {
//
//    private static SurveyStatApi surveyStatApi;
//
//    @BeforeAll
//    static void setup() {
//        surveyStatApi = new SurveyStatApi();
//    }
//
//    @Test
//    void testSurveyIds() {
//        surveyStatApi.testSurveyStatistic("Survey Id", "surveyId");
//    }
//
//    @Test
//    void testNumberOfCompletes() {
//        surveyStatApi.testSurveyStatistic("Number of Completes", "numberOfCompletes");
//    }
//
//    @Test
//    void testNumberOfFiltered() {
//        surveyStatApi.testSurveyStatistic("Number of Filtered", "numberOfFiltered");
//    }
//
//    @Test
//    void testNumberOfRejected() {
//        surveyStatApi.testSurveyStatistic("Number of Rejected", "numberOfRejected");
//    }
//
//    @Test
//    void testAverageLength() {
//        surveyStatApi.testSurveyStatistic("Average Length", "avgLengthOfSurvey");
//    }
//}