package main;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class TestFileSplitterEtl {

	@Test
	public void shouldSplitFile() throws Exception {
		String sourceFilePath = Objects.requireNonNull(TestFileSplitterEtl.class.getClassLoader().getResource("small" +
				".csv")).getPath();
		Path fullOutputFile = Path.of("full.csv");
		Path simpleOutputFile = Path.of("simple.csv");

		FileSplitterEtl etl = new FileSplitterEtl(sourceFilePath);
		etl.run();

		assertTrue(Files.exists(simpleOutputFile));
		assertThat(Files.readAllLines(simpleOutputFile).get(0)).isEqualTo("john,doe");
		assertTrue(Files.exists(fullOutputFile));
		assertThat(Files.readAllLines(fullOutputFile).get(0)).isEqualTo("john,doe,21");
	}


}
