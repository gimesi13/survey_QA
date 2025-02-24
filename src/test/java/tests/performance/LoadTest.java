package tests.performance;

import config.Config;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

@ExtendWith(AllureJunit5.class)
@Tag("performance")
@Epic("API Tests")
@Feature("Load Testing")
@Story("Survey Statistics Load Test")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Performance - Load Test")
@Owner("Performance Team")
public class LoadTest extends Simulation {

    private static final String BASE_URL = Config.BASE_URL;

    // HTTP Protocol Configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Define the test scenario
    private final ScenarioBuilder scn = scenario("API Load Test - Survey Statistics")
            .exec(session -> {
                Allure.step("Executing GET request to /surveys/statistics");
                return session;
            })
            .exec(
                    http("GET /surveys/statistics")
                            .get("/surveys/statistics")
                            .check(status().is(200))
            )
            .pause(1); // Simulate real user behavior with a small pause

    // Load Simulation
    {
        Allure.step("Starting Load Test with ramp-up, steady load, and ramp-down phases");
        setUp(
                scn.injectOpen(
                        nothingFor(5),          // Wait 5 seconds before starting
                        rampUsers(100).during(15),  // Gradually increase to 100 users over 15 sec
                        constantUsersPerSec(70).during(30), // Keep a steady load of 70 users/sec for 30 sec
                        rampUsers(10).during(10)  // Slow down at the end
                )
        ).protocols(httpProtocol)
                .assertions(
                        global().failedRequests().percent().lt(1.0),  // Ensure <1% failures
                        global().responseTime().percentile(95).lt(800),  // 95% of requests < 800ms
                        global().responseTime().max().lt(2500) // No request should take >2.5s
                );
    }
}
