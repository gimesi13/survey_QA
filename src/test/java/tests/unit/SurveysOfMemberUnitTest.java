package tests.unit;

import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.equalTo;

public class SurveysOfMemberUnitTest {
    @Test
    void testSurveysOfMember10Unit() {
        ApiCallHelper.get("/members/10").then()
                .assertThat()
                .body("[0].id", equalTo(2))
                .body("[0].expectedCompletes", equalTo(70))
                .body("[0].completionPoints", equalTo(15))
                .body("[0].filteredPoints", equalTo(4))

                .body("[5].id", equalTo(33))
                .body("[5].expectedCompletes", equalTo(20))
                .body("[5].completionPoints", equalTo(25))
                .body("[5].filteredPoints", equalTo(5));
    }

    @Test
    void testSurveysOfMember299Unit() {
        ApiCallHelper.get("/members/299").then()
                .assertThat()
                .body("[0].id", equalTo(3))
                .body("[0].expectedCompletes", equalTo(100))
                .body("[0].completionPoints", equalTo(10))
                .body("[0].filteredPoints", equalTo(2))

                .body("[3].id", equalTo(55))
                .body("[3].expectedCompletes", equalTo(70))
                .body("[3].completionPoints", equalTo(35))
                .body("[3].filteredPoints", equalTo(1));
    }
}