public class HuffmanTuple {
	public char letter;
	public String representation;

	public HuffmanTuple(char letter, String representation) {
		this.letter = letter;
		this.representation = representation;
	}

	@Override
	public String toString() {
		return this.letter + " => " + this.representation;
	}
}
