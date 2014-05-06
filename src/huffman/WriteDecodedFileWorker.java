package huffman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WriteDecodedFileWorker extends WriteFileWorker {

	private boolean firstByte;
	private int bytesToSkip;
	private Map<String, Character> map;
	private char charToWrite;

	public WriteDecodedFileWorker(String path, Map<String, Character> map) {
		super(path);
		this.map = map;
		this.bytesToSkip = 0;
		this.firstByte = true;
		this.charToWrite = (char) 0x00;
	}

	/**
	 * Internal handler for writing the data to the file
	 */
	public void writeToFile() {
		try {
			fileOutputStream.write(this.charToWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doWork(int currentByte) {
		// TODO: Handle reading in bytes and writing to file
		//System.out.println(currentByte + " -> " + Integer.toHexString(currentByte) + " -> " + Integer.toBinaryString(currentByte));

		if (firstByte) {
			// We're on the first byte which tells us how many encodings there are
			// We don't need to decode the dictionary, so we'll set how many bytes
			// we want to skip by multiplying this number by 2
			bytesToSkip = currentByte * 2;
			firstByte = false;
		} else if (bytesToSkip <= 0) {
			// Add the current byte into the buffer
			byteBuffer += Huffman.rightPadString(Integer.toBinaryString(currentByte), NUM_OF_BITS_TO_WRITE);
			int currentLength = 1;

			while (true) {
				try {
					String current = this.byteBuffer.substring(0, currentLength);
					Character possibility = this.map.get(current);
					if (possibility != null) {
						//System.out.println(possibility);
						this.byteBuffer = this.byteBuffer.substring(currentLength);
						this.charToWrite = possibility;
						this.writeToFile();
						currentLength = 1;
					} else {
						currentLength++;
					}
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
		} else {
			bytesToSkip--;
		}
	}
}
