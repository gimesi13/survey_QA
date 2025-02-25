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

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private final ScenarioBuilder scn = scenario("API Load Test")
            .exec(
                    http("GET /surveys/statistics")
                            .get("/surveys/statistics")
                            .check(status().is(200))
            )
            .pause(1);

    {
        setUp(
                scn.injectOpen(
                        nothingFor(5),
                        rampUsers(100).during(15),
                        constantUsersPerSec(70).during(30),
                        rampUsers(10).during(10)
                )
        ).protocols(httpProtocol)
                .assertions(
                        global().failedRequests().percent().lt(1.0),
                        global().responseTime().percentile(95).lt(1000),
                        global().responseTime().max().lt(3000)
                );
    }
}