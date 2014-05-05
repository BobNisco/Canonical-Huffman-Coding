package huffman;

import java.util.HashMap;
import java.util.Map;

public class ReadLookupCodes implements IFileReaderWorker {
	protected Map<Character, Integer> map;
	private int numberOfChars;
	private int currentCodeIndex;
	private boolean readChar;
	private char inputChar;

	public ReadLookupCodes() {
		this.map = new HashMap<>();
		this.numberOfChars = -1;
		this.currentCodeIndex = -1;
		this.readChar = false;
	}

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
