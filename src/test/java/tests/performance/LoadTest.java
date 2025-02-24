package tests.performance;

import config.Config;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.junit.jupiter.api.Tag;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

@Tag("performance")
public class LoadTest extends Simulation {

    private static final String BASE_URL = Config.BASE_URL;

    // HTTP Protocol Configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Define the test scenario
    private final ScenarioBuilder scn = scenario("API Load Test")
            .exec(
                    http("GET /surveys/statistics")
                            .get("/surveys/statistics")
                            .check(status().is(200))
            )
            .pause(1); // Simulate real user behavior with a small pause

    // Load Simulation
    {
        setUp(
                scn.injectOpen(
                        nothingFor(5),          // Wait 5 seconds before starting
                        rampUsers(100).during(15),  // Gradually increase to 1000 users over 30 sec
                        constantUsersPerSec(70).during(30), // Keep a steady load of 700 users/sec for 30 sec
                        rampUsers(10).during(10)  // Slow down at the end
                )
        ).protocols(httpProtocol)
                .assertions(
                        global().failedRequests().percent().lt(1.0),  // Ensure <1% failures
                        global().responseTime().percentile(95).lt(1000),  // 95% of requests <1s
                        global().responseTime().max().lt(3000) // No request should take >3s
                );
    }
}

