package huffman;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * A JUnit test class for the huffman.Encode class
 * @author Bob Nisco
 */
public class EncodeTest {

	protected Encode encode;

	@Before
	public void setUp() throws Exception {
		encode = new Encode();
	}

	@Test
	public void testHuffman00() throws Exception {
		// Set up a string with frequencies from the example from Pg 432 of CLRS
		// has modifications to deal with handling our EOF being added
		Map<Character, Integer> map = encode.createMapFromFile("samples/input/sample2.txt");

		Node rootNode = encode.huffman(map);

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

	@Test
	public void testCodeForLookup() throws Exception {
		ArrayList<HuffmanTuple> list = new ArrayList<>();
		list.add(new HuffmanTuple('a', "000"));
		list.add(new HuffmanTuple('b', "010"));
		list.add(new HuffmanTuple('c', "001"));
		list.add(new HuffmanTuple('d', "1"));
		list.add(new HuffmanTuple((char) 0x00, "011"));

		String generatedString = encode.generateLookupCode(list);
		String expectedString = "0561036203630364010003";
		assertEquals(expectedString, generatedString);
	}
}