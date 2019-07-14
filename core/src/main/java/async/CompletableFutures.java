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
					int number = ODD_INITIAL_VALUE;
					while (!isEven(number)) {
						log.info("Still searching, {} is not ok", number);
						number = slowlyGuessANumber();
					}
					completableFuture.complete(number);
					return null;
				});
		return completableFuture;
	}

	static Future<Boolean> slowlyFindAnEvenIntegerAndCheckIt() {
		CompletableFuture<Integer> findEven = CompletableFuture
				.supplyAsync(() -> {
					int number = ODD_INITIAL_VALUE;
					while (!isEven(number)) {
						log.info("Still searching, {} is not ok", number);
						number = slowlyGuessANumber();
					}
					return number;
				});
		return findEven.thenCompose(integer -> CompletableFuture.supplyAsync(() -> isEven(integer)));

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

