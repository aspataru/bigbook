package postgres;

import com.github.geowarin.junit.DockerRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Created by aspataru on 6/26/17.
 */
@Slf4j
public class TestPostgresIntegration {

    // docker run -p 5432:5432 --name some-postgres -e POSTGRES_PASSWORD= -d postgres

    @ClassRule
    public static DockerRule dockerRule =
            DockerRule.builder()
                    .image("postgres:9.6.3")
                    .ports("5432")
//                    .waitForPort("5672/tcp")
//                    .waitForLog("Server startup complete")
                    .build();

    @Test
    public void shouldConnectToPostgres() throws Exception {

    }
}
