package huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A decode class for a Huffman-encoded file
 * @author Bob Nisco
 */
public class Decode {
	private String sourceFilePath;
	private String targetFilePath;

	public Decode() {
		super();
	}

	public Decode(String sourceFilePath, String targetFilePath) {
		this.sourceFilePath = sourceFilePath;
		this.targetFilePath = targetFilePath;
	}

	/**
	 * The runner function to perform the decoding
	 */
	private void performDecode() {
		// 1. Read in file and build huffman tree
		Map<Character, Integer> map = Huffman.createMapFromEncodedFile(this.sourceFilePath);
		ArrayList<HuffmanTuple> tuples = this.convertMapToTuples(map);
		Huffman.sortHuffmanTuples(tuples);
		Huffman.canonizeEncodings(tuples);
		// 2. Store codes for lookup
		// 3. Decode data and write character output
	}

	/**
	 * Converts a map of Char -> Int into an ArrayList<HuffmanTuple>
	 *     and fills in the representations with values of the correct
	 *     length, but not the correctly canonized values
	 * @param map map of character to length count
	 * @return the list of HuffmanTuples
	 */
	private ArrayList<HuffmanTuple> convertMapToTuples(Map<Character, Integer> map) {
		ArrayList<HuffmanTuple> list = new ArrayList<>();
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			// Make up garbage representations based on how long the rep is
			// The real values will be created later
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < entry.getValue(); i++) {
				sb.append("1");
			}
			list.add(new HuffmanTuple(entry.getKey(), sb.toString()));
		}
		return list;
	}

	/**
	 * A main method for huffman.Decode
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Decode decode;

		if (args.length == 2) {
			decode = new Decode(args[0], args[1]);
		} else {
			// Sample data for ease of use when running while developing
			decode = new Decode("samples/output/sample2.huf", "samples/output/sample2_decoded.txt");
		}

		decode.performDecode();
	}
}
