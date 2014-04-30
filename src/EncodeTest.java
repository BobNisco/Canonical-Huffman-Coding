import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class EncodeTest {

	protected Encode encode;

	@Before
	public void setUp() throws Exception {
		encode = new Encode();
	}

	@Test
	public void testHuffman00() throws Exception {
		// Set up a string with frequencies from the example from Pg 432 of CLRS
		String sampleText = "fffffeeeeeeeeeccccccccccccbbbbbbbbbbbbbddddddddddddddddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		Map<Character, Integer> map = encode.createCharacterFrequencyMap(sampleText);

		Node rootNode = encode.huffman(map);

		// Build up expected tree
		Node expectedRootNode = new Node(100);
		expectedRootNode.left = new Node("a".charAt(0), 45);
		expectedRootNode.right = new Node(55);
		expectedRootNode.right.left = new Node(25);
		expectedRootNode.right.left.left = new Node("c".charAt(0), 12);
		expectedRootNode.right.left.right = new Node("b".charAt(0), 13);
		expectedRootNode.right.right = new Node(30);
		expectedRootNode.right.right.right = new Node("d".charAt(0), 16);
		expectedRootNode.right.right.left = new Node(14);
		expectedRootNode.right.right.left.left = new Node("f".charAt(0), 5);
		expectedRootNode.right.right.left.right = new Node("e".charAt(0), 9);

		// Compare each Node
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
}