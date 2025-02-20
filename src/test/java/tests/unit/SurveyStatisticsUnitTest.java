package tests.unit;

import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

public class SurveyStatisticsUnitTest {

    //Tests are failing because we are trying to validate data based on CSVs (that don't match the actual response data)

    @Test
    void testSurvey1Unit() {
        ApiCallHelper.get("/surveys/statistics").then()
                .assertThat()
                .body("surveyName", hasItem("Survey 01"))
                .body("[0].surveyId", equalTo(1))
                .body("[0].numberOfCompletes", equalTo(21))
                .body("[0].numberOfFiltered", equalTo(8))
                .body("[0].numberOfRejected", equalTo(4))
                .body("[0].avgLengthOfSurvey", equalTo(13));
    }
    @Test
    void testSurvey100Unit() {
        ApiCallHelper.get("/surveys/statistics").then()
                .assertThat()
                .body("surveyName", hasItem("Survey 100"))
                .body("[99].surveyId", equalTo(100))
                .body("[99].numberOfCompletes", equalTo(24))
                .body("[99].numberOfFiltered", equalTo(3))
                .body("[99].numberOfRejected", equalTo(5))
                .body("[99].avgLengthOfSurvey", equalTo(15));
    }
}