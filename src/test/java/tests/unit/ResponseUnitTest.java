//package tests.unit;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import api.BaseApi;
//
//public class ResponseUnitTest {
//    private static BaseApi baseApi;
//
//    @BeforeAll
//    static void setup() {
//        baseApi = new BaseApi();
//    }
//
//    //SURVEY POINTS
//    @Test
//    void testSurveyPointsStatusSuccess() {
//        baseApi.statusTest("/members/1/points", 200);
//        baseApi.statusTest("/members/300/points", 200);
//    }
//
//    @Test
//    void testSurveyPointsStatusFail() {
//        baseApi.statusTest("/members/test/points", 400);
//        baseApi.statusTest("members/1/point", 404);
//        baseApi.statusTest("/members/0/points", 500);
//        baseApi.statusTest("/members/999/points", 500);
//    }
//
//    //SURVEY STATISTICS
//    @Test
//    void testSurveyStatisticsStatusSuccess() {
//        baseApi.statusTest("/surveys/statistics", 200);
//    }
//
//    @Test
//    void testSurveyStatisticsStatusFail() {
//        baseApi.statusTest("/surveys/statistics/123", 404);
//    }
//
//    //AVAILABLE MEMBERS
//    @Test
//    void testAvailableMembersStatusSuccess() {
//        baseApi.statusTest("/surveys/1/members/not-invited", 200);
//        baseApi.statusTest("/surveys/100/members/not-invited", 200);
//    }
//
//    @Test
//    void testAvailableMembersStatusFail() {
//        baseApi.statusTest("/surveys/test/members/not-invited", 400);
//        baseApi.statusTest("/surveys/1/members/not-invited/123", 404);
//        baseApi.statusTest("/surveys/0/members/not-invited", 500);
//        baseApi.statusTest("/surveys/101/members/not-invited", 500);
//    }
//
//    //SURVEYS OF MEMBER
//    @Test
//    void testSurveysOfMemberStatusSuccess() {
//        baseApi.statusTest("/members/1", 200);
//        baseApi.statusTest("/members/300", 200);
//    }
//
//    @Test
//    void testSurveysOfMemberStatusFail() {
//        baseApi.statusTest("/members/test", 400);
//        baseApi.statusTest("/members/1/test", 404);
//        baseApi.statusTest("/members/0", 500);
//        baseApi.statusTest("/members/301", 500);
//    }
//
//    //MEMBERS OF SURVEY
//    @Test
//    void testMembersOfSurveyStatusSuccess() {
//        baseApi.statusTest("/surveys/1/members?status=NOT_ASKED", 200);
//        baseApi.statusTest("/surveys/25/members?status=REJECTED", 200);
//        baseApi.statusTest("/surveys/50/members?status=FILTERED", 200);
//        baseApi.statusTest("/surveys/100/members?status=COMPLETED", 200);
//    }
//
//    @Test
//    void testMembersOfSurveyStatusFail() {
//        baseApi.statusTest("/surveys/1/members?status=TEST", 400);
//        baseApi.statusTest("/surveys/1/members/123?status=NOT_ASKED", 404);
//        baseApi.statusTest("/surveys/0/members?status=FILTERED", 500);
//        baseApi.statusTest("/surveys/101/members?status=COMPLETED", 500);
//    }
//}