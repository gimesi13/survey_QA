package api;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import utils.ApiCallHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BaseApi {

    public void statusTest(String endpoint, int expectedStatus) {
        Response response = ApiCallHelper.get(endpoint);
        int actualStatus = response.statusCode();

        try {
            assertThat("Status code mismatch for: " + endpoint, actualStatus, equalTo(expectedStatus));
            Allure.step("Sending API request to: " + endpoint + "\n Expected: " + expectedStatus + " |  Actual: " + actualStatus);
        } catch (AssertionError e) {
            throw e;
        }
    }
}