package tests.unit;

import org.junit.jupiter.api.Test;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

public class AvailableMembersUnitTest {

    @Test
    void testAvailable1Unit() {
        ApiCallHelper.get("/surveys/1/members/not-invited").then()
                .assertThat()
                .body("id", hasItem(1))
                .body("id", not(hasItem(290)));
    }

    @Test
    void testAvailable50Unit() {
        ApiCallHelper.get("/surveys/1/members/not-invited").then()
                .assertThat()
                .body("id", hasItem(286))
                .body("id", not(hasItem(287)));
    }
}