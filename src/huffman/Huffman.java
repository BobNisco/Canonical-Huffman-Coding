package huffman;

import java.io.*;
import java.util.*;

/**
 * A class to handle all the Huffman algorithm operations
 * @author Bob Nisco
 */
public class Huffman {

	/**
	 * Implementation of the Huffman Algorithm as found on page 431 in CLRS textbook
	 * @param map a map of the character to frequencies
	 * @return the root node of the Huffman tree
	 */
	protected static Node huffman(Map<Character, Integer> map) {
		PriorityQueue<Node> q = new PriorityQueue<>(convertMapToList(map));

		for (int i = 1; i < map.size(); i++) {
			Node z = new Node();
			Node x = q.poll();
			z.left = x;
			Node y = q.poll();
			z.right = y;
			z.freq = x.freq + y.freq;
			z.letter = z.INTERIOR_NODE_CHAR;
			q.add(z);
		}
		return q.poll();
	}

	/**
	 * Internal static handler for performing recursive inorder traversal
	 * @param current the current node we're dealing with
	 * @param representation the current binary representation
	 * @param list the list to append new tuples to
	 */
	static void performInorderTraversal(Node current, String representation, ArrayList<HuffmanTuple> list) {
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
	protected static Map<Character, Integer> createMapFromFile(String filePath) {
		CreateFrequencyMap createFrequencyMap = new CreateFrequencyMap();
		// Manually insert the EOF marker into the map
		createFrequencyMap.map.put((char) 0x00, 1);
		Huffman.readFromFileAndDoWork(filePath, createFrequencyMap);
		return createFrequencyMap.map;
	}

	/**
	 * Function to turn a Huffman tree into a canonical Huffman Tree
	 * @param root the root node of the Huffman Tree
	 * @return the ArrayList of HuffmanTuples that represents the canonized encodings
	 */
	protected static ArrayList<HuffmanTuple> canonizeHuffmanTree(Node root) {
		// 1. Extract the encodings for each character
		ArrayList<HuffmanTuple> encodings = Huffman.extractEncodings(root);

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
			currentTuple.representation = Huffman.rightPadString(Integer.toBinaryString(currentNum), currentTuple.representation.length());
			currentNum++;
		}
		return encodings;
	}

	/**
	 * Generates the hex string representation for the lookup codes
	 * @param encodings a list of HuffmanTuples that is already in lexicographical order
	 * @return the hex string representation for the lookup codes
	 */
	protected static String generateLookupCode(ArrayList<HuffmanTuple> encodings) {
		StringBuilder builder = new StringBuilder();
		// 1. First will be the length of the list
		builder.append(Huffman.rightPadString(Integer.toHexString(encodings.size()), 2));
		// 2. For each of the letters, generate its hex encoding with length
		for (HuffmanTuple tuple : encodings) {
			builder.append(Huffman.rightPadString(Integer.toHexString((int) tuple.letter), 2));
			builder.append(Huffman.rightPadString(Integer.toHexString(tuple.representation.length()), 2));
		}
		return builder.toString();
	}

	/**
	 * The function to handle reading in a file from a path and using the canonical mapping
	 * to write to the output file
	 * @param inputPath the input file path
	 * @param outputPath the output file path
	 * @param encodings the canonical encodings
	 */
	protected static void writeToFile(String inputPath, String outputPath, ArrayList<HuffmanTuple> encodings) {
		WriteFile writeFile = new WriteFile(outputPath, encodings);
		writeFile.writeBeginningOfFile(Huffman.generateLookupCode(encodings));
		Huffman.readFromFileAndDoWork(inputPath, writeFile);
		writeFile.writeEndOfFile();
	}

	/**
	 * Utility method to pad our hex inputs to 2 spaces
	 * @param input the value to pad
	 * @param length the length of the whole string after padding
	 * @return the padded string
	 */
	public static String rightPadString(String input, int length) {
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
	private static ArrayList<HuffmanTuple> extractEncodings(Node root) {
		ArrayList<HuffmanTuple> list = new ArrayList<>();
		Huffman.performInorderTraversal(root, "", list);
		return list;
	}

	/**
	 * An internal handler for reading from a file and perform an action on it
	 * @param filePath the file path of the file to read from
	 * @param handler an instance of a class that implements huffman.IFileReader
	 *                so that it can call the overridden doWork() method
	 */
	private static void readFromFileAndDoWork(String filePath, IFileReader handler) {
		// Utilize Java 7's resources feature to auto-close the file
		// so that we don't need to use a finally block to close it
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			int currentByte;
			while ((currentByte = br.read()) != -1) {
				handler.doWork(currentByte);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file with the given path of: " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
