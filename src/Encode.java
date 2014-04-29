import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bob Nisco
 */

public class Encode {

	public Encode() {
		super();
	}

	/**
	 * Creates a map that counts the character frequencies
	 * @param text the text to be read through
	 * @return a Map<Character, Integer> where the key is the character
	 *         and the value is how many times the character appears
	 */
	private Map<Character, Integer> createCharacterFrequencyMap(String text) {
		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < text.length(); i++) {
			Character currentChar = text.charAt(i);
			Integer currentCharCount = map.get(currentChar);

			if (currentCharCount == null) {
				map.put(currentChar, 1);
			} else {
				map.put(currentChar, ++currentCharCount);
			}
		}
		return map;
	}

	/**
	 * An internal handler for reading from a file
	 * @param filePath the file path of the file to be read
	 * @return a string representation of the file
	 */
	private String readFromFile(String filePath) {
		// Utilize Java 7's resources feature to auto-close the file
		// so that we don't need to use a finally block to close it
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = br.readLine();
			}
			return stringBuilder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * A main method for Encode
	 * @param args
	 */
	public static void main(String[] args) {
		Encode encode = new Encode();
		String sourceFilePath;
		String targetFilePath;

		if (args.length == 2) {
			sourceFilePath = args[0];
			targetFilePath = args[1];
		} else {
			// Sample data for ease of use when running while developing
			sourceFilePath = "samples/input/sample1.txt";
			targetFilePath = "samples/output/sample1.txt";
		}

		String text = encode.readFromFile(sourceFilePath);
		System.out.println(text);

		Map<Character, Integer> map = encode.createCharacterFrequencyMap(text);
		System.out.println(map.toString());
	}
}
