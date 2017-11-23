package main;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFileStreamerDemo {

    private static final String BIG_TEST_FILE_PATH = "/home/aspataru/data/worldbank/" +
            "Data_Extract_From_Gender_Statistics/Data.csv";

    @Test
    public void shouldCountLinesForBigFilesUsingRxJava() throws Exception {
        FileStreamerDemo fs = new FileStreamerDemo(BIG_TEST_FILE_PATH);
        assertEquals(165696, fs.countLinesUsingRxJava());
    }

}