package tests.unit;

import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

public class MembersOfSurveyUnitTest {
    @Test
    void testMembersOfSurveyCompleted1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=COMPLETED").then()
                .assertThat()
                .body("id", hasItem(2))
                .body("id", not(hasItem(1)));
    }

    @Test
    void testMembersOfSurveyRejected1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=REJECTED").then()
                .assertThat()
                .body("id", hasItem(38))
                .body("id", not(hasItem(3)));
    }
    @Test
    void testMembersOfSurveyNotAsked1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=NOT_ASKED").then()
                .assertThat()
                .body("$", hasSize(0));
    }
    @Test
    void testMembersOfSurveyFiltered1Unit() {
        ApiCallHelper.get("/surveys/1/members?status=FILTERED").then()
                .assertThat()
                .body("id", hasItem(35))
                .body("id", not(hasItem(4)));
    }
}