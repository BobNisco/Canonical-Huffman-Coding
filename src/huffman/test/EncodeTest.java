package huffman.test;

import huffman.Encode;
import huffman.Huffman;
import huffman.HuffmanTuple;
import huffman.Node;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the huffman.Encode class
 * @author Bob Nisco <BobNisco@gmail.com>
 */
public class EncodeTest {

	/**
	 * Tests the creation of a Huffman Tree
	 * @throws Exception
	 */
	@Test
	public void testHuffman00() throws Exception {
		// Set up a string with frequencies from the example from Pg 432 of CLRS
		// has modifications to deal with handling our EOF being added
		Map<Character, Integer> map = Huffman.createMapFromFile("samples/input/sample2.txt");

		Node rootNode = Huffman.huffman(map);

		// Build up expected tree
		Node expectedRootNode = new Node(101);
		expectedRootNode.left = new Node('a', 45);
		expectedRootNode.right = new Node(56);
		expectedRootNode.right.left = new Node(25);
		expectedRootNode.right.left.left = new Node('c', 12);
		expectedRootNode.right.left.right = new Node('b', 13);
		expectedRootNode.right.right = new Node(31);
		expectedRootNode.right.right.right = new Node('d', 16);
		expectedRootNode.right.right.left = new Node(15);
		expectedRootNode.right.right.left.left = new Node(6);
		expectedRootNode.right.right.left.left.right = new Node('f', 5);
		expectedRootNode.right.right.left.left.left = new Node((char) 0x00, 1);
		expectedRootNode.right.right.left.right = new Node('e', 9);

		// Compare each huffman.Node
		assertEquals(expectedRootNode, rootNode);
		assertEquals(expectedRootNode.left, rootNode.left);
		assertEquals(expectedRootNode.right, rootNode.right);
		assertEquals(expectedRootNode.right.left, rootNode.right.left);
		assertEquals(expectedRootNode.right.left.left, rootNode.right.left.left);
		assertEquals(expectedRootNode.right.left.right, rootNode.right.left.right);
		assertEquals(expectedRootNode.right.right, rootNode.right.right);
		assertEquals(expectedRootNode.right.right.right, rootNode.right.right.right);
		assertEquals(expectedRootNode.right.right.left, rootNode.right.right.left);
		assertEquals(expectedRootNode.right.right.left.left, rootNode.right.right.left.left);
		assertEquals(expectedRootNode.right.right.left.right, rootNode.right.right.left.right);
	}

	/**
	 * Tests the creation of a lookup code that would be stored at the beginning of an encoded file
	 * @throws Exception
	 */
	@Test
	public void testCodeForLookup() throws Exception {
		ArrayList<HuffmanTuple> list = new ArrayList<>();
		list.add(new HuffmanTuple('a', "000"));
		list.add(new HuffmanTuple('b', "010"));
		list.add(new HuffmanTuple('c', "001"));
		list.add(new HuffmanTuple('d', "1"));
		list.add(new HuffmanTuple((char) 0x00, "011"));

		String generatedString = Huffman.generateLookupCode(list);
		String expectedString = "0500036103620363036401";
		assertEquals(expectedString, generatedString);
	}

	@Test
	public void testEncode01() throws Exception {
		this.testEncode("samples/provided-samples/text/sample1.txt", "samples/provided-samples/encoded/sample1_encoded.huf", "samples/provided-samples/encoded/sample1.huf");
	}

	@Test
	public void testEncode02() throws Exception {
		this.testEncode("samples/provided-samples/text/sample2.txt", "samples/provided-samples/encoded/sample2_encoded.huf", "samples/provided-samples/encoded/sample2.huf");
	}

	@Test
	public void testEncode03() throws Exception {
		this.testEncode("samples/provided-samples/text/sample3.txt", "samples/provided-samples/encoded/sample3_encoded.huf", "samples/provided-samples/encoded/sample3.huf");
	}

	@Test
	public void testEncode04() throws Exception {
		this.testEncode("samples/provided-samples/text/sample4.txt", "samples/provided-samples/encoded/sample4_encoded.huf", "samples/provided-samples/encoded/sample4.huf");
	}

	@Test
	public void testEncode05() throws Exception {
		this.testEncode("samples/provided-samples/text/sample5.txt", "samples/provided-samples/encoded/sample5_encoded.huf", "samples/provided-samples/encoded/sample5.huf");
	}

	private void testEncode(String inputFile, String encodedFile, String expectedEncodedFile) throws Exception {
		String[] args = new String[2];
		args[0] = inputFile;
		args[1] = encodedFile;
		Encode.main(args);

		File createdFile = new File(args[1]);
		File expectedFile = new File(expectedEncodedFile);
		assertEquals(expectedFile.length(), createdFile.length());
	}
}