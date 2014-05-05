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
public class WriteEncodedFile extends FileWriterWorker {

	public WriteEncodedFile(String path, ArrayList<HuffmanTuple> encodings) {
		super(path, encodings);
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
