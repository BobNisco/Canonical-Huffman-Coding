package huffman;

import java.util.ArrayList;
import java.util.Map;

public class WriteDecodedFileWorker extends WriteFileWorker {

	private boolean firstByte = true;
	private int bytesToSkip = 0;
	private Map<String, Character> map;

	public WriteDecodedFileWorker(String path, Map<String, Character> map) {
		super(path);
		this.map = map;
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
			byteBuffer += Integer.toBinaryString(currentByte);
			int currentLength = 1;

			while (true) {
				try {
					String current = this.byteBuffer.substring(0, currentLength);
					Character possibility = this.map.get(current);
					if (possibility != null) {
						System.out.println(possibility);
						this.byteBuffer = this.byteBuffer.substring(currentLength);
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
