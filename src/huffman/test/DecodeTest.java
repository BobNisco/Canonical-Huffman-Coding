package huffman.test;

import huffman.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the huffman.Decode class
 * @author Bob Nisco <BobNisco@gmail.com>
 */
public class DecodeTest {

	@Test
	public void testDecode01() throws Exception {
		this.testDecode("samples/provided-samples/encoded/sample1_encoded.huf", "samples/provided-samples/encoded/sample1_decoded.txt", "samples/provided-samples/text/sample1.txt");
	}

	@Test
	public void testDecode02() throws Exception {
		this.testDecode("samples/provided-samples/encoded/sample2_encoded.huf", "samples/provided-samples/encoded/sample2_decoded.txt", "samples/provided-samples/text/sample2.txt");
	}

	@Test
	public void testDecode03() throws Exception {
		this.testDecode("samples/provided-samples/encoded/sample3_encoded.huf", "samples/provided-samples/encoded/sample3_decoded.txt", "samples/provided-samples/text/sample3.txt");
	}

	@Test
	public void testDecode04() throws Exception {
		this.testDecode("samples/provided-samples/encoded/sample4_encoded.huf", "samples/provided-samples/encoded/sample4_decoded.txt", "samples/provided-samples/text/sample4.txt");
	}

	@Test
	public void testDecode05() throws Exception {
		this.testDecode("samples/provided-samples/encoded/sample5_encoded.huf", "samples/provided-samples/encoded/sample5_decoded.txt", "samples/provided-samples/text/sample5.txt");
	}

	private void testDecode(String encodedFile, String outputFile, String expectedFilePath) throws Exception {
		String[] args = new String[2];
		args[0] = encodedFile;
		args[1] = outputFile;
		Decode.main(args);

		File createdFile = new File(args[1]);
		File expectedFile = new File(expectedFilePath);
		assertEquals(expectedFile.length(), createdFile.length());
	}
}
