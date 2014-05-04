package huffman;

/**
 * A huffman.Node class that will be used in the Huffman algorithm
 * @author Bob Nisco
 */
public class Node implements Comparable<Node> {
	public Node left;
	public Node right;
	public int freq;
	public char letter;

	public Node() {
	}

	public Node(int freq) {
		this.freq = freq;
		this.letter = (char) 0x01;
	}

	public Node(Node left, Node right, int freq, char letter) {
		this.left = left;
		this.right = right;
		this.freq = freq;
		this.letter = letter;
	}

	public Node(char letter, int freq) {
		this.left = null;
		this.right = null;
		this.freq = freq;
		this.letter = letter;
	}

	@Override
	public String toString() {
		return this.letter + " => " + this.freq;
	}

	/**
	 * Since we will be using these nodes in a Priority Queue, we will
	 * implement the Comparable class so they are properly sorted
	 * @param o the huffman.Node to compare to
	 * @return standard numeric representation for comparison
	 */
	@Override
	public int compareTo(Node o) {
		if (this.freq > o.freq) {
			return 1;
		} else if (this.freq < o.freq) {
			return -1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Node o = (Node) obj;
		if ((this.freq == o.freq) && (this.letter == o.letter)) {
			return true;
		}
		return false;
	}
}
