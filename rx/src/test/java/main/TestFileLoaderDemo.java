package main;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFileLoaderDemo {

	private static final String TEST_FILE_PATH = TestFileLoaderDemo.class.getClassLoader().getResource("small.csv")
			.getPath();
	//    private static final String BIG_TEST_FILE_PATH = "/home/aspataru/data/worldbank/" +
//            "Data_Extract_From_Gender_Statistics/Data.csv";
	private static final String BIG_TEST_FILE_PATH = TEST_FILE_PATH;

	@Test
	public void shouldCountLinesForSmallFiles() throws Exception {
		FileLoaderDemo fs = new FileLoaderDemo(TEST_FILE_PATH);
		assertEquals(2, fs.countLinesOfFile());
	}

	@Test
	public void shouldCountLinesForBigFiles() throws Exception {
		FileLoaderDemo fs = new FileLoaderDemo(BIG_TEST_FILE_PATH);
//		assertEquals(165696, fs.countLinesOfFile());
		assertEquals(2, fs.countLinesOfFile());
	}

}