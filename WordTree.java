import java.util.List;


public class WordTree {
	private static class WordNode {
		public String word;
		public int count;
		public int height;
		public WordNode left;
		public WordNode right;
	}
	
	private WordNode root = null;
	
	public void add(String word) {
		root = add(root, word);
	}
	
	private static WordNode add(WordNode node, String key) {
		if(node == null) {
			//when a leaf is reached create new node and add it
			node = new WordNode();
			node.word = key;
			node.count = 1;
			node.height = 1;
		} else if(key.compareTo(node.word) == 0) {
			//if element already exists increase counter
			node.count++;
		}
		else {
			//recursively add to subtree
			if(key.compareTo(node.word) < 0)
				node.left = add(node.left, key);
			else
				node.right = add(node.right, key);
			//fix balance if necessary
			if(balanceFactor(node) == -2) { 			//left-left and left-right case
				//in left-right case do a left rotation first 
				if(balanceFactor(node.left) == 1)
					node.left = rotateLeft(node.left);
				//in both cases rotate right
				node = rotateRight(node);
			} else if(balanceFactor(node) == 2) { 		//right-right and right-left case
				//in right-left case do a right rotation first 
				if(balanceFactor(node.right) == -1)
					node.right = rotateRight(node.right);
				//in both cases rotate left
				node = rotateLeft(node);
			}
			//update height
			node.height = maxHeight(node.left, node.right) + 1;
		}
		return node;
	}
	
	public boolean find(String word) {
		return find(root, word);
	}
	
	private static boolean find(WordNode node, String word) {
		if(node == null)
			return false;
		if(word.compareTo(node.word) == 0)
			return true;
		else if(word.compareTo(node.word) < 0)
			return find(node.left, word);
		else
			return find(node.right, word);
	}
	
	public void findUnmatched( List<String> words, WordTree dictionary ) {
		findUnmatched(root, words, dictionary);
	}
	
	private static void findUnmatched( WordNode node, List<String> words, WordTree dictionary ) {
		if(node != null) {
			findUnmatched(node.left, words, dictionary);
			if( ! dictionary.find(node.word) )
				words.add(node.word);
			findUnmatched(node.right, words, dictionary);
		}
	}
	
	public void preorder() {
		preorder(root);
	}
	
	private static void preorder(WordNode node) {
		if(node == null)
			System.out.print(". ");
		else {
			System.out.print(node.word + " ");
			preorder(node.left);
			preorder(node.right);
		}
	}
	
	private static int maxHeight(WordNode a, WordNode b) {
		if(a == null && b == null)
			return 0;
		if(a == null)
			return b.height;
		if(b == null)
			return a.height;
		return (a.height > b.height) ? a.height : b.height;
	}
	
	private static int balanceFactor(WordNode node) {
		int l = 0, r = 0;
		if(node.left != null)
			l = node.left.height;
		if(node.right != null)
			r = node.right.height;
		return r - l;
	}
	
	private static WordNode rotateRight(WordNode node) {
		WordNode leftChild = node.left;
		node.left = leftChild.right;
		leftChild.right = node;
		return leftChild;
	}
	
	private static WordNode rotateLeft(WordNode node) {
		WordNode rightChild = node.right;
		node.right = rightChild.left;
		rightChild.left = node;
		return rightChild;
	}
	
	public String toString() {
		return inorder(root, "");
	}
	
	private static String inorder(WordNode node, String s) {
		String temp = "";
		if(node != null) {
			temp += inorder(node.left, s);
			temp += node.word + " " + node.count + "\n";
			temp += inorder(node.right, s);
		}
		return temp;
	}
}
