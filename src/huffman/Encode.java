package huffman;

import java.io.*;
import java.util.*;

/**
 * An huffman.Encode class for a canonical Huffman Tree
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
			// We will use 0x01 ASCII code to denote these types of new nodes
			// Originally, it would have just been 0x00, but since we use that
			// for the EOF marker, we need to change the behavior.
			z.letter = (char) 0x01;
			q.add(z);
		}
		return q.poll();
	}

	/**
	 * Function to turn a Huffman tree into a canonical Huffman Tree
	 * @param root the root node of the Huffman Tree
	 * @return the ArrayList of HuffmanTuples that represents the canonized encodings
	 */
	protected ArrayList<HuffmanTuple> canonizeHuffmanTree(Node root) {
		// 1. Extract the encodings for each character
		ArrayList<HuffmanTuple> encodings = this.extractEncodings(root);

		// 2. Sort by length of binary representation
		//    If there is a tie, sort by lexicographical order
		Collections.sort(encodings, new Comparator<HuffmanTuple>() {
			@Override
			public int compare(HuffmanTuple o1, HuffmanTuple o2) {
				if (o1.representation.length() > o2.representation.length()) {
					return 1;
				} else if (o1.representation.length() < o2.representation.length()) {
					return -1;
				} else {
					// We have same length representation
					// Need to break tie by sorting on letter
					if (o1.letter > o2.letter) {
						return 1;
					} else if (o1.letter < o2.letter) {
						return -1;
					}
				}
				return 0;
			}
		});

		// 3. Change each representation based on canonical order
		int currentNum = 0;
		for (int i = 0; i < encodings.size() - 1; i++) {
			HuffmanTuple currentTuple = encodings.get(i);
			if (currentTuple.representation.length() > Integer.toBinaryString(currentNum).length()) {
				currentNum = currentNum << 1;
			}
			currentTuple.representation = this.rightPadString(Integer.toBinaryString(currentNum), currentTuple.representation.length());
			currentNum++;
		}
		return encodings;
	}

	/**
	 * Generates the hex string representation for the lookup codes
	 * @param encodings a list of HuffmanTuples that is already in lexicographical order
	 * @return the hex string representation for the lookup codes
	 */
	protected String generateLookupCode(ArrayList<HuffmanTuple> encodings) {
		StringBuilder builder = new StringBuilder();
		// 1. First will be the length of the list
		builder.append(this.rightPadString(Integer.toHexString(encodings.size()), 2));
		// 2. For each of the letters, generate its hex encoding with length
		for (HuffmanTuple tuple : encodings) {
			builder.append(this.rightPadString(Integer.toHexString((int) tuple.letter), 2));
			builder.append(this.rightPadString(Integer.toHexString(tuple.representation.length()), 2));
		}
		return builder.toString();
	}

	protected void writeToFile(String inputPath, String outputPath, ArrayList<HuffmanTuple> encodings) {
		WriteFile writeFile = new WriteFile(outputPath, encodings);
		this.readFromFileAndDoWork(inputPath, writeFile);
		writeFile.writeEndOfFile();
	}

	/**
	 * Utility method to pad our hex inputs to 2 spaces
	 * @param input the value to pad
	 * @param length the length of the whole string after padding
	 * @return the padded string
	 */
	private String rightPadString(String input, int length) {
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		return sb.toString().substring(input.length()) + input;
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
		if (current.letter != (char) 0x01) {
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
	 * An internal handler for reading from a file and creating the
	 * frequency map
	 * Utilizes a buffered reader and input stream reader so it can handle large files
	 * @param filePath the file path of the file to be read
	 * @return a map that maps the char to how many times it appears in the file
	 */
	protected Map<Character, Integer> createMapFromFile(String filePath) {
		CreateFrequencyMap createFrequencyMap = new CreateFrequencyMap();
		// Manually insert the EOF marker into the map
		createFrequencyMap.map.put((char) 0x00, 1);
		this.readFromFileAndDoWork(filePath, createFrequencyMap);
		return createFrequencyMap.map;
	}

	/**
	 * An internal handler for reading from a file and perform an action on it
	 * @param filePath the file path of the file to read from
	 * @param handler an instance of a class that implements huffman.IFileReader
	 *                so that it can call the overridden doWork() method
	 */
	private void readFromFileAndDoWork(String filePath, IFileReader handler) {
		// Utilize Java 7's resources feature to auto-close the file
		// so that we don't need to use a finally block to close it
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			int currentByte;
			while ((currentByte = br.read()) != -1) {
				handler.doWork(currentByte);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A main method for huffman.Encode
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

		Map<Character, Integer> map = encode.createMapFromFile(sourceFilePath);
		System.out.println(map.toString());

		Node rootNode = encode.huffman(map);

		ArrayList<HuffmanTuple> encodings = encode.canonizeHuffmanTree(rootNode);
		System.out.println(encodings);

		encode.writeToFile(sourceFilePath, targetFilePath, encodings);
	}
}
