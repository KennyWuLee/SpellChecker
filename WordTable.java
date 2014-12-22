import java.util.ArrayList;
import java.util.List;

public class WordTable {
	
	private WordTree[] wordtrees;
	
	public WordTable() {
		wordtrees = new WordTree[26];
		for(int i = 0; i < wordtrees.length; ++i)
			wordtrees[i] = new WordTree();
	}
	
	public void add(String word) {
		wordtrees[(int)word.charAt(0) - (int)'a'].add(word);
	}
	
	public String toString() {
		String s = "";
		for(WordTree t : wordtrees)
			s += t.toString();
		return s;
	}
	
	public List<String> findUnmatched(WordTable dictionary) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < wordtrees.length; ++i)
			wordtrees[i].findUnmatched(list, dictionary.wordtrees[i]);
		return list;
	}
}
