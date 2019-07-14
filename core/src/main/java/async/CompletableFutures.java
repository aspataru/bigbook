package async;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
class CompletableFutures {

	private CompletableFutures() {
	}

	static Future<Integer> slowlyFindEvenInteger() {
		CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

		Executors.newCachedThreadPool().submit(
				() -> {
					int number = -1;
					while (!isEven(number)) {
						log.info("Still searching, {} is not ok", number);
						number = slowlyGuessANumber();
					}
					completableFuture.complete(number);
					return null;
				});
		return completableFuture;
	}

	private static int slowlyGuessANumber() throws InterruptedException {
		Thread.sleep(500);
		return new Random().nextInt();
	}

	private static boolean isEven(long number) {
		return number % 2 == 0;
	}


}

