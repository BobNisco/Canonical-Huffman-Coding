package huffman;

import java.io.IOException;
import java.util.Map;

/**
 * Our worker class to handle reading in an encoded file and outputting
 * a decoded version of that file.
 * @author Bob Nisco <BobNisco@gmail.com>
 */
public class WriteDecodedFileWorker extends WriteFileWorker {

	private boolean firstByte;
	private int bytesToSkip;
	private Map<String, Character> map;
	private char charToWrite;
	private boolean endOfFile;

	/**
	 * Constructor for our write decoded file worker
	 * @param path the output path
	 * @param map character mapping
	 */
	public WriteDecodedFileWorker(String path, Map<String, Character> map) {
		super(path);
		this.map = map;
		this.bytesToSkip = 0;
		this.firstByte = true;
		this.charToWrite = (char) 0x00;
		this.endOfFile = false;
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

	/**
	 * Overriden doWork method. Takes in the current byte from the read in file,
	 * reads in the binary data, converts to chars and writes to the output file
	 * @param currentByte the current byte from the input file
	 */
	@Override
	public void doWork(int currentByte) {
		if (this.endOfFile) {
			return;
		}
		if (this.firstByte) {
			// We're on the first byte which tells us how many encodings there are
			// We don't need to decode the dictionary, so we'll set how many bytes
			// we want to skip by multiplying this number by 2
			this.bytesToSkip = currentByte * 2;
			this.firstByte = false;
		} else if (bytesToSkip <= 0) {
			// Add the current byte into the buffer
			this.byteBuffer += Huffman.rightPadString(Integer.toBinaryString(currentByte), NUM_OF_BITS_TO_WRITE);
			int currentLength = 1;

			while (true) {
				try {
					String current = this.byteBuffer.substring(0, currentLength);
					Character possibility = this.map.get(current);
					if (possibility != null) {
						if (possibility == '\u0000') {
							this.endOfFile = true;
							break;
						}
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
