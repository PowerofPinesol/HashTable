import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The Hash table/Spell checker program creates a hash table of words from a file, i.e. a dictionary.
 * SpellChecker will run to see if there was a typo in user inputed word, and print an alphabetically
 * sorted list of every potential word the user could have meant in the dictionary.
 *
 * @author  Aric Schroeder
 * @version 1.0
 * @since   2021-10-26
 */


public class Program {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("WordList.txt");
        int tableSize = HashTable.getTableSize("WordList.txt");
        HashTable table = new HashTable(tableSize);
        Files.lines(path).forEach(s -> table.add(s));
        Scanner userInput = new Scanner(System.in);

//        **Code for finding actual collisions, and expected collisions**
//        System.out.println(table.getCollisions());
//        System.out.println(HashTable.expectedCollisions(table.size(), tableSize));

        boolean isFinished = false;
        while(!isFinished) {
            System.out.print("\nSearch for word: ");
            String wordToCheck = userInput.nextLine();

            SpellChecker spellChecker = new SpellChecker(table, wordToCheck);

            if(table.contains(wordToCheck)) {
                System.out.println("ok");
            } else if(spellChecker.findNearbyWordsInDictionary().length > 0) {
                System.out.println("Table did not contain '" + wordToCheck + "', did you mean: \n");
                String[] matchedWords = spellChecker.findNearbyWordsInDictionary();
                String[] sortedMatches = spellChecker.alphabetize(matchedWords);
                spellChecker.printWords(sortedMatches);
            } else {
                System.out.println("Not found.");
            }
        }
    }
}
