import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class SpellChecker {

	public static void main(String[] args) {
		
//		WordTree test = new WordTree();
//		test.add("a");
//		test.add("b");
//		test.add("c");
//		test.add("d");
//		test.add("e");
//		test.add("f");
//		test.add("g");
//		test.add("h");
//		
//		test.preorder();
//		
//		System.out.println(test.find("a"));
//		System.out.println(test.find("x"));
//		System.out.println(test.find("b"));

		
		try {
			int threads;
			String documentFilename, dictionaryFilename;
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter a document to spellcheck: ");
			documentFilename = scan.nextLine();
			System.out.print("Enter a dictionary to use: ");
			dictionaryFilename = scan.nextLine();
			System.out.print("Enter the number of threads to use: ");
			threads = scan.nextInt();
			scan.nextLine();
			scan.close();

			WordTable dictionary = new WordTable();
			scan = new Scanner(new File(dictionaryFilename));
			while(scan.hasNext())
				dictionary.add(scan.nextLine());
			scan.close();
			
			WordTable words = new WordTable();
			scan = new Scanner(new File(documentFilename));			
			String word;
			while((word = scan.findWithinHorizon("[A-Za-z]([A-Za-z-\']*[A-Za-z])?", 0)) != null)
				words.add(word.toLowerCase());
			scan.close();
			
			PrintWriter writer = new PrintWriter("words.txt");
			scan = new Scanner(words.toString());
			while(scan.hasNext())
				writer.println(scan.nextLine());
			scan.close();
			writer.close();
			
			writer = new PrintWriter("misspelled.txt");
			List<String> list = words.findUnmatched(dictionary);
			for(String s : list)
				writer.println(s);
			writer.close();
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
