package huffman;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to handle creating a frequency map from an input file.
 * Implements huffman.IFileReader so that we can pass the doWork() function
 * into the function that actually reads a file
 * @author Bob Nisco
 */
public class CreateFrequencyMap implements IFileReaderWorker {

	Map<Character, Integer> map;
	private char currentChar;

	/**
	 * Constructor
	 */
	public CreateFrequencyMap() {
		this.map = new HashMap<>();
	}

	/**
	 * Overridden doWork() method. Takes in the current byte, and modifies the
	 * current frequency map
	 * @param currentByte the current byte from the input file
	 */
	@Override
	public void doWork(int currentByte) {
		currentChar = (char) currentByte;

		if (map.get(currentChar) == null) {
			map.put(currentChar, 1);
		} else {
			map.put(currentChar, map.get(currentChar) + 1);
		}
	}
}
