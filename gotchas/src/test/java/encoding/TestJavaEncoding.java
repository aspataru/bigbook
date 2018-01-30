package encoding;

import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
/**
 * play around with -Dfile.encoding=... to check the effects on our tests
 */
public class TestJavaEncoding {

	private static final String RESOURCE_PATH = TestJavaEncoding.class.getClassLoader().getResource("files").getPath
			().toString();

	private final String fileName;
	private final String expectedFirstLine;
	private final Charset charset;

	private String fullFilePath;

	@Parameterized.Parameters(name = "{0}")
	public static Object[][] parameters() {
		return new Object[][]{
				{"utf8.txt", "HELLOП", Charset.forName("UTF-8")}
				//	un-commenting will break the test
				//	,{"utf16.txt", "HELLOП", Charset.forName("UTF-16")}
		};
	}

	@Before
	public void setFullFilePath() {
		fullFilePath = Paths.get(RESOURCE_PATH, fileName).toAbsolutePath().toString();
	}

	@Test
	public void shouldReadFirstLineWithJava7DefaultEncoding() throws Exception {
		List<String> lines = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fullFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
		Assert.assertEquals(expectedFirstLine, lines.get(0));
	}

	@Test
	public void shouldReadFirstLineWithJava7SpecifiedEncoding() throws Exception {
		List<String> lines = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fullFilePath), charset)
		)) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
		Assert.assertEquals(expectedFirstLine, lines.get(0));
	}

	@Test
	public void shouldReadFirstLineWithJava8DefaultEncoding() throws Exception {
		List<String> lines = Files.readAllLines(Paths.get(RESOURCE_PATH, fileName));

		Assert.assertEquals(expectedFirstLine, lines.get(0));
	}

	@Test
	public void shouldReadFirstLineWithJava8SpecifiedEncoding() throws Exception {
		List<String> lines = Files.readAllLines(Paths.get(RESOURCE_PATH, fileName), charset);

		Assert.assertEquals(expectedFirstLine, lines.get(0));
	}


}
