import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to handle writing to the file while simultaneously reading in from a file
 * with a buffer so that we can take the input, convert it into our Huffman Encoding
 * and write it.
 * @author Bob Nisco
 */
public class WriteFile implements IFileReader {
	public Map<Character, Integer> map;
	public File file;
	public FileOutputStream fileOutputStream;

	/**
	 * Constructor that takes in the file path of the file to write to
	 * @param path the path to the file to write to
	 */
	public WriteFile(String path) {
		this.map = new HashMap<>();
		this.file = new File(path);
		this.initFile();
	}

	/**
	 * Handle all the initialization of the fileOutputStream and File
	 */
	private void initFile() {
		try {
			this.fileOutputStream = new FileOutputStream(this.file);
			// Ensure that the file exists before writing to it
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overridden doWork method. Takes the current byte, finds the character ir represents
	 * in our map, and then writes the representation to a file
	 * @param currentByte the current byte from the input file
	 */
	@Override
	public void doWork(int currentByte) {
		try {
			// TODO: Use our encoding to convert the current character
			//       to our binary representation before writing it
			fileOutputStream.write(currentByte);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
