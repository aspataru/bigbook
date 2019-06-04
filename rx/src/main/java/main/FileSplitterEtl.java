package main;

import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class FileSplitterEtl {

	private final String sourceFilePath;

	public void run() {
		ConnectableFlowable<String[]> flowable =
				Flowable.fromIterable(() -> new FileIterable(sourceFilePath).iterator())
						.skip(1)
						.doOnNext(s -> log.info("Read {}", s))
						.map(s -> s.split(","))
						.map(trimStringsInArrayFunction())
						.publish();

		try (BufferedWriter fullWriter = new BufferedWriter(new FileWriter("full.csv"));
			 BufferedWriter simpleWriter = new BufferedWriter(new FileWriter("simple.csv"))) {

			flowable.subscribe(strings -> Flowable.just(strings)
					.map(FullPersonDetails::new)
					.forEach(fullPersonDetails -> fullWriter.write(fullPersonDetails.toString() + "\n")));

			flowable.subscribe(strings -> Flowable.just(strings)
					.map(SimplePersonDetails::new)
					.forEach(simplePersonDetails -> simpleWriter.write(simplePersonDetails.toString() + "\n")));

			flowable.connect();

		} catch (Exception e) {
			log.error("Error writing to file", e);
		}

	}

	private static Function<String[], String[]> trimStringsInArrayFunction() {
		return strings -> Stream.of(strings)
				.map(String::trim)
				.toArray(String[]::new);
	}

	@RequiredArgsConstructor
	private static class FileIterable implements Iterable<String> {

		private final String filePath;

		@Override
		public Iterator<String> iterator() {
			try {
				return Files.lines(Path.of(filePath)).iterator();
			} catch (IOException e) {
				throw new IllegalStateException("Something went wrong when opening the file " + filePath, e);
			}
		}
	}


	private class FullPersonDetails {
		String firstName;
		String lastName;
		Integer age;

		FullPersonDetails(String[] args) {
			this.firstName = args[0];
			this.lastName = args[1];
			this.age = Integer.parseInt(args[2]);
		}

		public String toString() {
			return firstName + "," + lastName + "," + age;
		}
	}

	private class SimplePersonDetails {
		String firstName;
		String lastName;

		SimplePersonDetails(String[] args) {
			this.firstName = args[0];
			this.lastName = args[1];
		}

		public String toString() {
			return firstName + "," + lastName;
		}
	}

}
