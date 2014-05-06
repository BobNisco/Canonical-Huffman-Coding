package huffman;

import java.util.ArrayList;
import java.util.Map;

public class WriteDecodedFileWorker extends WriteFileWorker {

	private boolean firstByte = true;
	private int bytesToSkip = 0;

	public WriteDecodedFileWorker(String path, Map<Character, String> map) {
		super(path, map);
	}

	@Override
	public void doWork(int currentByte) {
		// TODO: Handle reading in bytes and writing to file
		System.out.println(currentByte + " -> " + Integer.toHexString(currentByte) + " -> " + Integer.toBinaryString(currentByte));
		byteBuffer += Huffman.rightPadString(Integer.toBinaryString(currentByte), NUM_OF_BITS_TO_WRITE);
//		if (firstByte) {
//			bytesToSkip = currentByte;
//			firstByte = false;
//		} else if (bytesToSkip <= 0) {
//			byteBuffer += Integer.toBinaryString(currentByte);
//			int currentLength = 1;
////			while (true) {
////				try {
////					String current = this.byteBuffer.substring(0, currentLength + 1);
////					String possibility = this.map.get(current);
////				} catch (IndexOutOfBoundsException e) {
////					break;
////				}
////			}
//		} else {
//			bytesToSkip--;
//		}
	}
}
