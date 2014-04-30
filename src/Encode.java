import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Bob Nisco
 */

public class Encode {

	public Encode() {
		super();
	}

	/**
	 * Implementation of the Huffman Algorithm as found on page 431 in CLRS textbook
	 * @param map a map of the character to frequencies
	 * @return the root node of the Huffman tree
	 */
	protected Node huffman(Map<Character, Integer> map) {
		PriorityQueue<Node> q = new PriorityQueue<>(convertMapToList(map));

		for (int i = 1; i < map.size(); i++) {
			Node z = new Node();
			Node x = q.poll();
			z.left = x;
			Node y = q.poll();
			z.right = y;
			z.freq = x.freq + y.freq;
			q.add(z);
		}
		return q.poll();
	}

	private Node canonicalHuffmanTree(Node root) {
		// First we'll extract the encodings for each character
		ArrayList<HuffmanTuple> encodings = this.extractEncodings(root);
		for (HuffmanTuple t : encodings) {
			System.out.println(t.toString());
		}

		return root;
	}

	/**
	 * Extract the representations of the characters
	 * after the Huffman Tree has been made.
	 * @param root the root node of the Huffman Tree
	 * @return
	 */
	private ArrayList<HuffmanTuple> extractEncodings(Node root) {
		ArrayList<HuffmanTuple> list = new ArrayList<>();
		Encode.performInorderTraversal(root, "", list);
		return list;
	}

	/**
	 * Internal static handler for performing recursive inorder traversal
	 * @param current the current node we're dealing with
	 * @param representation the current binary representation
	 * @param list the list to append new tuples to
	 */
	private static void performInorderTraversal(Node current, String representation, ArrayList<HuffmanTuple> list) {
		if (current.left != null) {
			performInorderTraversal(current.left, representation + "0", list);
		}
		if (current.letter != '\u0000') {
			list.add(new HuffmanTuple(current.letter, representation));
		}
		if (current.right != null) {
			performInorderTraversal(current.right, representation + "1", list);
		}
	}

	/**
	 * Utility function to convert a map to a list of nodes
	 * Used for seeding the PriorityQueue in the Huffman Algorithm
	 * @param map
	 * @return
	 */
	private static ArrayList<Node> convertMapToList(Map<Character, Integer> map) {
		ArrayList<Node> list = new ArrayList<>();
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			list.add(new Node(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	/**
	 * Creates a map that counts the character frequencies
	 * @param text the text to be read through
	 * @return a Map<Character, Integer> where the key is the character
	 *         and the value is how many times the character appears
	 */
	protected Map<Character, Integer> createCharacterFrequencyMap(String text) {
		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < text.length(); i++) {
			Character currentChar = text.charAt(i);
			Integer currentCharCount = map.get(currentChar);

			if (currentCharCount == null) {
				map.put(currentChar, 1);
			} else {
				map.put(currentChar, ++currentCharCount);
			}
		}
		return map;
	}

	/**
	 * An internal handler for reading from a file
	 * @param filePath the file path of the file to be read
	 * @return a string representation of the file
	 */
	private String readFromFile(String filePath) {
		// Utilize Java 7's resources feature to auto-close the file
		// so that we don't need to use a finally block to close it
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = br.readLine();
			}
			return stringBuilder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * A main method for Encode
	 * @param args
	 */
	public static void main(String[] args) {
		Encode encode = new Encode();
		String sourceFilePath;
		String targetFilePath;

		if (args.length == 2) {
			sourceFilePath = args[0];
			targetFilePath = args[1];
		} else {
			// Sample data for ease of use when running while developing
			sourceFilePath = "samples/input/sample2.txt";
			targetFilePath = "samples/output/sample2.txt";
		}

		String text = encode.readFromFile(sourceFilePath);
		System.out.println(text);

		Map<Character, Integer> map = encode.createCharacterFrequencyMap(text);
		System.out.println(map.toString());

		Node rootNode = encode.huffman(map);

		encode.canonicalHuffmanTree(rootNode);
	}
}
