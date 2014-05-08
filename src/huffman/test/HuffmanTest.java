package huffman.test;

import huffman.Decode;
import huffman.Encode;
import org.junit.Test;

import java.io.File;

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

	@Test
	public void testEncodeAndDecode01() throws Exception {
		HuffmanTest.testEncodeAndDecode("samples/input/sample1.txt", "samples/output/sample1_encoded.huf", "samples/output/sample1_decoded.txt");
	}

	@Test
	public void testEncodeAndDecode02() throws Exception {
		HuffmanTest.testEncodeAndDecode("samples/input/sample2.txt", "samples/output/sample2_encoded.huf", "samples/output/sample2_decoded.txt");
	}

	@Test
	public void testEncodeAndDecode03() throws Exception {
		HuffmanTest.testEncodeAndDecode("samples/input/sample3.txt", "samples/output/sample3_encoded.huf", "samples/output/sample3_decoded.txt");
	}

	@Test
	public void testEncodeAndDecode04() throws Exception {
		HuffmanTest.testEncodeAndDecode("samples/input/sample4.txt", "samples/output/sample4_encoded.huf", "samples/output/sample4_decoded.txt");
	}

	protected static void testEncodeAndDecode(String inputFile, String encodedFile, String decodedFile) throws Exception {
		String[] args = new String[2];
		args[0] = inputFile;
		args[1] = encodedFile;
		Encode.main(args);

		args[0] = encodedFile;
		args[1] = decodedFile;
		Decode.main(args);

		assertEquals(new File(inputFile).length(), new File(decodedFile).length());
	}

	protected static void testEncodeAndDecodeSample(String inputFile, String encodedFile, String expectedEncodedFile, String decodedFile) throws Exception {
		// Encode the file and assert
		EncodeTest.testEncode(inputFile, encodedFile, expectedEncodedFile);
		// Decode the file and assert
		DecodeTest.testDecode(encodedFile, decodedFile, inputFile);
	}
}
