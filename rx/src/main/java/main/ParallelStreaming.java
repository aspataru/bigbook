package main;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ParallelStreaming {

	private static final int N_THREADS = 3;
	private static final int MAX_MESSAGES = 1000;

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
		Scheduler scheduler = Schedulers.from(executorService);
		AtomicInteger producerCounter = new AtomicInteger(0);
		Flowable<Integer> flowable = Flowable.generate(Random::new, (random, emitter) -> {
			if (producerCounter.getAndIncrement() < MAX_MESSAGES) {
				emitter.onNext(random.nextInt());
			} else {
				emitter.onComplete();
			}
		});

		AtomicInteger messageCounter = new AtomicInteger(0);
		Single<Long> count = flowable
				.groupBy(i -> messageCounter.getAndIncrement() % N_THREADS)
				.doOnNext(g -> log.info("G: {}", g.getKey()))
				.flatMap(group -> group.observeOn(scheduler).doOnNext(integer -> log.info("hi {}", integer)))
				.count();

		log.info("Seen {} messages", count.blockingGet());

		executorService.shutdownNow();
	}

}
