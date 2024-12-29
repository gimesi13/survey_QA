package tests.unit;

import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

public class SurveyPointsUnitTest {

    @Test
    void testPoints1Unit() {
        ApiCallHelper.get("/members/1/points").then()
                .assertThat()
                .body("survey.name", hasItems("Survey 16", "Survey 25", "Survey 45", "Survey 79", "Survey 82", "Survey 95"))
                .body("[0].survey.id", equalTo(16))
                .body("[0].survey.expectedCompletes", equalTo(90))
                .body("[0].survey.completionPoints", equalTo(45))
                .body("[0].survey.filteredPoints", equalTo(3))
                .body("[0].point", equalTo(45))

                .body("[5].survey.id", equalTo(95))
                .body("[5].survey.expectedCompletes", equalTo(80))
                .body("[5].survey.completionPoints", equalTo(15))
                .body("[5].survey.filteredPoints", equalTo(5))
                .body("[5].point", equalTo(0));
    }

    @Test
    void testPoints300Unit() {
        ApiCallHelper.get("/members/300/points").then()
                .assertThat()
                .body("survey.name", hasItems("Survey 05", "Survey 12", "Survey 24", "Survey 46", "Survey 53"))
                .body("[0].survey.id", equalTo(5))
                .body("[0].survey.expectedCompletes", equalTo(20))
                .body("[0].survey.completionPoints", equalTo(35))
                .body("[0].survey.filteredPoints", equalTo(4))
                .body("[0].point", equalTo(35))

                .body("[4].survey.id", equalTo(53))
                .body("[4].survey.expectedCompletes", equalTo(70))
                .body("[4].survey.completionPoints", equalTo(20))
                .body("[4].survey.filteredPoints", equalTo(5))
                .body("[4].point", equalTo(20));
    }
}