package huffman;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that implements the IFileReaderWorker interface.
 * Used to read in the first x bytes from the encoded file and
 * creates the Map of Characters to Integers
 * @author Bob Nisco <BobNisco@gmail.com>
 */
public class ReadLookupCodesWorker implements IFileReaderWorker {
	protected Map<Character, Integer> map;
	private int numberOfChars;
	private int currentCodeIndex;
	private boolean readChar;
	private char inputChar;

	/**
	 * No-args constructor
	 */
	public ReadLookupCodesWorker() {
		this.map = new HashMap<>();
		this.numberOfChars = -1;
		this.currentCodeIndex = -1;
		this.readChar = false;
	}

	/**
	 * The overridden doWork() method.
	 * Takes in the current byte from the read-in file
	 * and generates the map.
	 * @param currentByte the current byte
	 */
	@Override
	public void doWork(int currentByte) {
		char currentChar = (char) currentByte;

		if (this.numberOfChars == -1) {
			// We haven't yet set how many characters we need to read in
			this.numberOfChars = currentChar;
			this.currentCodeIndex = 0;
			this.readChar = true;
		} else {
			if (this.currentCodeIndex < this.numberOfChars) {
				if (readChar) {
					map.put(currentChar, 0);
					this.inputChar = currentChar;
					this.readChar = false;
				} else {
					map.put(this.inputChar, currentByte);
					this.readChar = true;
					currentCodeIndex++;
				}
			}
		}
	}
}
