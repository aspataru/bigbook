package async;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Duration;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

@Slf4j
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

	@Test
	public void should_Find_Even_Integer_Not_Using_Assertions() {
		Future<Boolean> succeedInFindingAnEvenInteger = CompletableFutures.slowlyFindAnEvenIntegerAndCheckIt();
		assertNotNull(succeedInFindingAnEvenInteger);
		await()
				.atMost(Duration.FIVE_SECONDS)
				.until(succeedInFindingAnEvenInteger::get);

	}

	@Test
	public void use_Parallel_Execution() {
		Executor executor = Executors.newFixedThreadPool(3);

		CompletableFuture<String> future1
				= CompletableFuture.supplyAsync(() -> supplyString(500, "Hello"), executor);
		CompletableFuture<String> future2
				= CompletableFuture.supplyAsync(() -> supplyString(500, "Beautiful"), executor);
		CompletableFuture<String> future3
				= CompletableFuture.supplyAsync(() -> supplyString(500, "World"), executor);

		String combined = Stream.of(future1, future2, future3)
				.map(CompletableFuture::join)
				.collect(Collectors.joining(" "));

		assertEquals("Hello Beautiful World", combined);
	}

	@Test
	public void should_Use_Exception_Handling() throws Exception {
		Future<Integer> futureWithFallback = CompletableFutures.panickyFindEvenIntegerWithFallback();

		await()
				.atMost(Duration.FIVE_SECONDS)
				.until(futureWithFallback::isDone);
		assertNotNull(futureWithFallback.get());
	}

	private String supplyString(long millis, String stringToSupply) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.info("Interrupted", e);
			Thread.currentThread().interrupt();
		}
		log.info("Supplying {}", stringToSupply);
		return stringToSupply;
	}
}