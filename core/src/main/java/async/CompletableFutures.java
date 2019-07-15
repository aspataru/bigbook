package async;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
class CompletableFutures {

	private static final int ODD_INITIAL_VALUE = -1;

	private CompletableFutures() {
	}

	static Future<Integer> slowlyFindEvenInteger() {
		CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

		Executors.newSingleThreadExecutor().submit(
				() -> {
					int number = keepSearchingForANumber();
					completableFuture.complete(number);
					return null;
				});
		return completableFuture;
	}

	static Future<Boolean> slowlyFindAnEvenIntegerAndCheckIt() {
		CompletableFuture<Integer> findEven = CompletableFuture
				.supplyAsync(CompletableFutures::keepSearchingForANumber);
		return findEven.thenCompose(integer -> CompletableFuture.supplyAsync(() -> isEven(integer)));
	}

	static Future<Integer> panickyFindEvenIntegerWithFallback() {
		return CompletableFuture.supplyAsync(CompletableFutures::guessEvenNumberAndPanicIfNotFound)
				.handle((integer, throwable) -> integer == null ? 0 : integer);
	}

	private static int keepSearchingForANumber() {
		int number = ODD_INITIAL_VALUE;
		while (!isEven(number)) {
			log.info("Still searching, {} is not ok", number);
			number = slowlyGuessANumber();
		}
		return number;
	}

	private static int guessEvenNumberAndPanicIfNotFound() {
		int number = slowlyGuessANumber();
		if (!isEven(number)) {
			log.error("I failed!");
			throw new IllegalStateException("I failed");
		}
		return number;
	}

	private static int slowlyGuessANumber() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			log.info("Interrupted");
			Thread.currentThread().interrupt();
		}
		return new Random().nextInt();
	}

	private static boolean isEven(int number) {
		return number % 2 == 0;
	}

}

