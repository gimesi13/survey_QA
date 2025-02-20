package tests.unit;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ApiCallHelper;

import static org.hamcrest.Matchers.*;

@ExtendWith(AllureJunit5.class)
@DisplayName("Unit - Available members")
public class AvailableMembersUnitTest {

    @Feature("Unit tests")
    @Story("Story annotation")
    @Tag("API")
    @Severity(SeverityLevel.NORMAL)
    @Test
    @DisplayName("Verify member '1'")
    @Description("This test verifies that member 1 is available.")
    void testAvailable1Unit() {
        ApiCallHelper.get("/surveys/1/members/not-invited").then()
                .assertThat()
                .body("id", hasItem(1))
                .body("id", not(hasItem(290)));
    }

    @Test
    @DisplayName("Verify member '50'")
    @Description("This test verifies that member 50 is available.")
    void testAvailable50Unit() {
        ApiCallHelper.get("/surveys/1/members/not-invited").then()
                .assertThat()
                .body("id", hasItem(286))
                .body("id", not(hasItem(287)));
    }
}