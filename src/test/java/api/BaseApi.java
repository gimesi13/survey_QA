package api;

import utils.ApiCallHelper;

public class BaseApi {

    public void statusTest(String endpoint, int expectedStatus) {
        System.out.println("endpoint: " + endpoint + ", expectedStatus: " + expectedStatus);
        ApiCallHelper.get(endpoint)
                .then()
                .statusCode(expectedStatus);
    }
}