package huffman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class WriteFileWorker implements IFileReaderWorker {
	public Map<Character, String> map;
	private File file;
	private FileOutputStream fileOutputStream;
	public String byteBuffer;
	public static final int NUM_OF_BITS_TO_WRITE = 8;

	protected WriteFileWorker() {
	}

	/**
	 * Constructor that takes in the file path of the file to write to
	 * @param path the path to the file to write to
	 * @param encodings the canonical encodings
	 */
	public WriteFileWorker(String path, ArrayList<HuffmanTuple> encodings) {
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

	/**
	 * Internal handler for writing the data to the file
	 */
	public void writeToFile() {
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
}
