package huffman;

import java.util.ArrayList;

public class WriteDecodedFile extends FileWriterWorker {

	public WriteDecodedFile(String path, ArrayList<HuffmanTuple> encodings) {
		super(path, encodings);
	}

	@Override
	public void doWork(int currentByte) {
		// TODO: Handle reading in bytes and writing to file
	}
}
