package async;

import org.awaitility.Duration;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestCompletableFutures {

	@Test
	public void should_Find_Even_Number_Within_5_Seconds() throws Exception {
		Future<Integer> future = CompletableFutures.slowlyFindEvenInteger();

		assertNotNull(future);

		await()
				.atMost(Duration.FIVE_SECONDS)
				.with()
				.pollInterval(Duration.ONE_HUNDRED_MILLISECONDS)
				.until(future::isDone);

		assertTrue(future.get() % 2 == 0);
	}

}