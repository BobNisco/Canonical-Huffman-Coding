package huffman;

/**
 * An interface for doing work while we're reading in a file
 * @author Bob Nisco
 */
public interface IFileReaderWorker {
	public void doWork(int currentByte);
}