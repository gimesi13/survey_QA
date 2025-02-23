package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class AssertionHelper {

    public static void assertValues(String fieldName, List<Integer> expected, List<Integer> actual) {
        try {
            assertThat("Mismatch in " + fieldName, actual, containsInAnyOrder(expected.toArray()));
        } catch (AssertionError e) {
            logAssertionError(fieldName, expected, actual, e);
            throw e;
        }
    }

    @Step("❌ Assertion Failed: {fieldName}")
    private static void logAssertionError(String fieldName, List<Integer> expected, List<Integer> actual, AssertionError e) {
        String errorMessage = "Assertion failed for " + fieldName + "\n" +
                "🔹 Expected: " + expected + "\n" +
                "🔻 Actual: " + actual + "\n" +
                "❗ Error: " + e.getMessage();
        Allure.addAttachment("Assertion Error - " + fieldName, errorMessage);
    }
}