package huffman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to handle writing to the file while simultaneously reading in from a file
 * with a buffer so that we can take the input, convert it into our Huffman Encoding
 * and write it.
 * @author Bob Nisco
 */
public class WriteFile implements IFileReaderWorker {
	private Map<Character, String> map;
	private File file;
	private FileOutputStream fileOutputStream;
	private String byteBuffer;
	private final int NUM_OF_BITS_TO_WRITE = 8;

	/**
	 * Constructor that takes in the file path of the file to write to
	 * @param path the path to the file to write to
	 * @param encodings the canonical encodings
	 */
	public WriteFile(String path, ArrayList<HuffmanTuple> encodings) {
		this.map = new HashMap<>();
		this.file = new File(path);
		this.byteBuffer = "";
		this.initFile();
		this.initMap(encodings);
	}

	/**
	 * Handle all the initialization of the fileOutputStream and File
	 */
	private void initFile() {
		try {
			this.fileOutputStream = new FileOutputStream(this.file);
			// Ensure that the file exists before writing to it
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the internal map with the encodings that were passed in
	 * @param encodings the ArrayList of HuffmanTuples that will be converted
	 *                  into a map for quicker lookup times
	 */
	private void initMap(ArrayList<HuffmanTuple> encodings) {
		for (HuffmanTuple tuple : encodings) {
			map.put(tuple.letter, tuple.representation);
		}
	}

	protected void writeBeginningOfFile(String headerString) {
		for (int i = 0; i < headerString.length(); i += 2) {
			int intRep = Integer.parseInt(headerString.substring(i, i + 2), 16);
			String bin = Integer.toBinaryString(intRep);
			String paddedBin = Huffman.rightPadString(bin, NUM_OF_BITS_TO_WRITE);
			byteBuffer += paddedBin;
			this.writeToFile();
		}
	}

	/**
	 * Writes the remaining bits and the end of file representation
	 */
	protected void writeEndOfFile() {
		byteBuffer += map.get((char) 0x00);
		this.writeToFile();
	}

	/**
	 * Internal handler for writing the data to the file
	 */
	private void writeToFile() {
		try {
			while (byteBuffer.length() >= NUM_OF_BITS_TO_WRITE) {
				int i = Integer.parseInt(byteBuffer.substring(0, NUM_OF_BITS_TO_WRITE), 2);
				fileOutputStream.write(i);
				byteBuffer = byteBuffer.substring(NUM_OF_BITS_TO_WRITE, byteBuffer.length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overridden doWork method. Takes the current byte, finds the character ir represents
	 * in our map, and then writes the representation to a file
	 * @param currentByte the current byte from the input file
	 */
	@Override
	public void doWork(int currentByte) {
		byteBuffer += map.get((char) currentByte);
		this.writeToFile();
	}
}
