package huffman.test;

import huffman.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the whole Huffman encode/decode
 * @author Bob Nisco <BobNisco@gmail.com>
 */
public class HuffmanTest {

	@Test
	public void testEncodeAndDecodeSample01() throws Exception {
		HuffmanTest.testEncodeAndDecodeSample("samples/provided-samples/text/sample1.txt", "samples/provided-samples/encoded/sample1_encoded.huf", "samples/provided-samples/encoded/sample1.huf", "samples/provided-samples/encoded/sample1_decoded.txt");
	}

	@Test
	public void testEncodeAndDecodeSample02() throws Exception {
		HuffmanTest.testEncodeAndDecodeSample("samples/provided-samples/text/sample2.txt", "samples/provided-samples/encoded/sample2_encoded.huf", "samples/provided-samples/encoded/sample2.huf", "samples/provided-samples/encoded/sample2_decoded.txt");
	}

	@Test
	public void testEncodeAndDecodeSample03() throws Exception {
		HuffmanTest.testEncodeAndDecodeSample("samples/provided-samples/text/sample3.txt", "samples/provided-samples/encoded/sample3_encoded.huf", "samples/provided-samples/encoded/sample3.huf", "samples/provided-samples/encoded/sample3_decoded.txt");
	}

	@Test
	public void testEncodeAndDecodeSample04() throws Exception {
		HuffmanTest.testEncodeAndDecodeSample("samples/provided-samples/text/sample4.txt", "samples/provided-samples/encoded/sample4_encoded.huf", "samples/provided-samples/encoded/sample4.huf", "samples/provided-samples/encoded/sample4_decoded.txt");
	}

	@Test
	public void testEncodeAndDecodeSample05() throws Exception {
		HuffmanTest.testEncodeAndDecodeSample("samples/provided-samples/text/sample5.txt", "samples/provided-samples/encoded/sample5_encoded.huf", "samples/provided-samples/encoded/sample5.huf", "samples/provided-samples/encoded/sample5_decoded.txt");
	}

	protected static void testEncodeAndDecodeSample(String inputFile, String encodedFile, String expectedEncodedFile, String decodedFile) throws Exception {
		// Encode the file and assert
		EncodeTest.testEncode(inputFile, encodedFile, expectedEncodedFile);
		// Decode the file and assert
		DecodeTest.testDecode(encodedFile, decodedFile, inputFile);
	}
}
