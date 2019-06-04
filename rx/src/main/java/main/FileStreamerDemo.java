package main;

import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;

@RequiredArgsConstructor
public class FileStreamerDemo {

	private final String filePath;

	public long countLinesUsingRxJava() {
		Flowable<String> source = Flowable.using(
				() -> new BufferedReader(new FileReader(filePath)),
				reader -> Flowable.fromIterable(() -> reader.lines().iterator()),
				BufferedReader::close
		);

		return source
				// .subscribeOn(Schedulers.computation())
				// .observeOn(Schedulers.io())
				// .subscribe(System.out::println, Throwable::printStackTrace);
				.count()
				.blockingGet();
	}

}