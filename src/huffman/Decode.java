package huffman;

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
		// 2. Store codes for lookup
		// 3. Decode data and write character output
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
			decode = new Decode("samples/input/sample2.txt", "samples/output/sample2.txt");
		}

		decode.performDecode();
	}
}
