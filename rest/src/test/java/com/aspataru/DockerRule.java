package com.aspataru;

import lombok.extern.slf4j.Slf4j;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Slf4j
public class DockerRule implements TestRule {

    private static final String CMD = "docker run --rm -it -d -p 5432:5432 -e POSTGRES_PASSWORD=pass " +
            "-v /home/aspataru/workspace/bigbook/db/src/test/resources/sql:/docker-entrypoint-initdb.d " +
            "postgres:9.6.3";

    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Process process = Runtime.getRuntime().exec(CMD);
                String dockerContainerID = null;
                try (final BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    dockerContainerID = reader.readLine();

                    waitForPort(5432);
                    log.info("Started docker container with id {}", dockerContainerID);

                    base.evaluate();

                } finally {
                    // Destroying the process directly doesn't work for some reason
                    // process.destroy();
                    if (dockerContainerID != null) {
                        stopContainerWithId(dockerContainerID);
                    }
                }
            }
        };
    }

    private void waitForPort(int port) throws Exception {
        int attempts = 5;
        long sleepTime = 2000;
        while (attempts > 0) {
            try (Socket socket = new Socket("localhost", port)) {
                log.info("Connection established to port {}", port);
                // even after the connection can be established, need to wait a bit
                Thread.sleep(sleepTime);
                return;
            } catch (IOException e) {
                attempts--;
                log.info("Cannot connect to port {}, attempts left {}", port, attempts);
                Thread.sleep(sleepTime);
            }
        }
    }

    private void stopContainerWithId(String id) throws Exception {
        Process process = Runtime.getRuntime().exec("docker rm -f " + id);
        process.waitFor();
        log.info("Stopped docker container with ID {}", id);
    }
}
