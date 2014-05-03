import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * An Encode class for a canonical Huffman Tree
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

	/**
	 * Function to turn a Huffman tree into a canonical Huffman Tree
	 * @param root the root node of the Huffman Tree
	 * @return the root node of the canonical Huffman Tree
	 */
	private Node canonicalHuffmanTree(Node root) {
		// 1. Extract the encodings for each character
		ArrayList<HuffmanTuple> encodings = this.extractEncodings(root);

		// 2. Sort by length of binary representation
		Collections.sort(encodings, new Comparator<HuffmanTuple>() {
			@Override
			public int compare(HuffmanTuple o1, HuffmanTuple o2) {
				if (o1.representation.length() > o2.representation.length()) {
					return 1;
				} else if (o1.representation.length() < o2.representation.length()) {
					return -1;
				}
				return 0;
			}
		});

		// 3. For each letter with the same representation size,
		//    sort those into alphabetical order
		int startIndex = 0;
		int endIndex = 0;
		int currentSize = 0;
		int currentIndex = 0;

		while (true) {
			try {
				if (encodings.get(currentIndex).representation.length() == currentIndex) {
					endIndex = currentIndex;
				}
				if (encodings.get(currentIndex).representation.length() > currentSize || currentIndex == encodings.size() - 1) {
					Collections.sort(encodings.subList(startIndex, endIndex), new Comparator<HuffmanTuple>() {
						@Override
						public int compare(HuffmanTuple o1, HuffmanTuple o2) {
							if (o1.letter > o2.letter) {
								return 1;
							} else if (o1.letter < o2.letter) {
								return -1;
							}
							return 0;
						}
					});

					currentSize = encodings.get(currentIndex).representation.length();
					startIndex = currentIndex;
				}
				currentIndex++;
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}

		for (HuffmanTuple t : encodings) {
			System.out.println(t.toString());
		}

		return root;
	}

	/**
	 * Generates the hex string representation for the lookup codes
	 * @param encodings a list of HuffmanTuples that is already in lexicographical order
	 * @return the hex string representation for the lookup codes
	 */
	protected String generateLookupCode(ArrayList<HuffmanTuple> encodings) {
		StringBuilder builder = new StringBuilder();
		// 1. First will be the length of the list
		builder.append(this.padHex(Integer.toHexString(encodings.size())));
		// 2. For each of the letters, generate its hex encoding with length
		for (HuffmanTuple tuple : encodings) {
			builder.append(this.padHex(Integer.toHexString((int) tuple.letter)));
			builder.append(this.padHex(Integer.toHexString(tuple.representation.length())));
		}
		return builder.toString();
	}

	/**
	 * Utility method to pad our hex inputs to 2 spaces
	 * @param input the value to pad
	 * @return the padded value
	 */
	private String padHex(String input) {
		return "00".substring(input.length()) + input;
	}

	/**
	 * Extract the representations of the characters
	 * after the Huffman Tree has been made.
	 * @param root the root node of the Huffman Tree
	 * @return an ArrayList of HuffmanTuples that maps a character to an encoding
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
		if (current == null) {
			return;
		}

		performInorderTraversal(current.left, representation + "0", list);
		if (current.letter != '\u0000') {
			list.add(new HuffmanTuple(current.letter, representation));
		}
		performInorderTraversal(current.right, representation + "1", list);
	}

	/**
	 * Utility function to convert a map to a list of nodes
	 * Used for seeding the PriorityQueue in the Huffman Algorithm
	 * @param map a map of characters to integers
	 * @return an arraylist of nodes
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
	 * @param args command line arguments
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
