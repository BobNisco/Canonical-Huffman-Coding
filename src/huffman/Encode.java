package huffman;

import java.util.ArrayList;
import java.util.Map;

/**
 * An huffman.Encode class for a canonical Huffman Tree
 * @author Bob Nisco
 */
public class Encode {

	private String sourceFilePath;
	private String targetFilePath;

	public Encode() {
		super();
	}

	/**
	 * Constructor that takes in the file path to the source and target file
	 * @param sourceFilePath input file path
	 * @param targetFilePath output file path
	 */
	public Encode(String sourceFilePath, String targetFilePath) {
		this.sourceFilePath = sourceFilePath;
		this.targetFilePath = targetFilePath;
	}

	/**
	 * The runner function to perform the encoding
	 */
	private void performEncode() {
		// 1. Create the character mapping by reading in from source file
		Map<Character, Integer> map = Huffman.createMapFromFile(this.sourceFilePath);
		// 2. Create the Huffman Tree
		Node rootNode = Huffman.huffman(map);
		// 3. Canonize the Huffman Tree
		ArrayList<HuffmanTuple> encodings = Huffman.canonizeHuffmanTree(rootNode);
		// 4. Write the file based on the encoding
		Huffman.writeEncodedFile(this.sourceFilePath, this.targetFilePath, encodings);
	}

	/**
	 * A main method for huffman.Encode
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Encode encode;

		if (args.length == 2) {
			encode = new Encode(args[0], args[1]);
		} else {
			// Sample data for ease of use when running while developing
			encode = new Encode("samples/input/sample3.txt", "samples/output/sample3.huf");
		}

		encode.performEncode();
	}
}
