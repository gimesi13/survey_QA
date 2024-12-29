package utils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class AssertionHelper {
    public static void assertValues(String fieldName, List<Integer> expected, List<Integer> actual) {

        try {
            assertThat("Mismatch in " + fieldName, actual, containsInAnyOrder(expected.toArray()));
        } catch (AssertionError e) {
            System.err.println("Assertion failed for " + fieldName + ". Details:");
            System.err.println("Expected: " + expected);
            System.err.println("Actual: " + actual);
            throw e;
        }
    }
}
