package api;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import utils.ApiCallHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BaseApi {

    public void statusTest(String endpoint, int expectedStatus) {
        Allure.step("Sending API request to: " + endpoint);
        Response response = ApiCallHelper.get(endpoint);
        int actualStatus = response.statusCode();

        Allure.step("Sending API request to: " + endpoint + "\n  ✅ Expected: " + expectedStatus + " | 🔹 Actual: " + actualStatus);

        try {
            assertThat("Status code mismatch for: " + endpoint, actualStatus, equalTo(expectedStatus));
        } catch (AssertionError e) {
            Allure.addAttachment("❌ API Failure - " + endpoint, "text/plain",
                    "🔴 Status Code Mismatch\n" +
                            "🔹 Expected: " + expectedStatus + "\n" +
                            "🔸 Actual: " + actualStatus + "\n\n" +
                            "📄 Response Body:\n" + response.body().asPrettyString()
            );
            throw e;
        }
    }
}