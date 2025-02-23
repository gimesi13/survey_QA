package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import config.Config;

import static io.restassured.RestAssured.baseURI;

public class ApiCallHelper {

    static {
        baseURI = Config.BASE_URL;
    }

    public static Response get(String endpoint) {
        return RestAssured.given()
                .when()
                .get(baseURI + endpoint)
                .thenReturn();
    }

    public static Response post(String endpoint, Object body) {
        return RestAssured.given()
                .body(body)
                .when()
                .post(baseURI + endpoint)
                .thenReturn();
    }

    public static Response put(String endpoint, Object body) {
        return RestAssured.given()
                .body(body)
                .when()
                .put(baseURI + endpoint)
                .thenReturn();
    }

    public static Response delete(String endpoint) {
        return RestAssured.given()
                .when()
                .delete(baseURI + endpoint)
                .thenReturn();
    }
}