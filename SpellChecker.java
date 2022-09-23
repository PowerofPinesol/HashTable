import java.util.Arrays;

public class SpellChecker {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    HashTable table;
    String word;
    int numOfMatches = 0;
    String[] matches = new String[1000]; // quick arraylist implementation for matches

    public SpellChecker(HashTable table, String word) {
        this.table = table;
        this.word = word;
    }

    /**
     * Takes a Set as input, and returns it as an alphabetically sorted array
     * @param arrayToAlphabetize
     * @return
     */
    public String[] alphabetize(String[] arrayToAlphabetize) {
        String[] deduped = Arrays.stream(arrayToAlphabetize).distinct().toArray(String[]::new);
        Arrays.sort(deduped);
        return deduped;
    }

    /**
     * Prints each string in an array
     * @param sortedArray
     */
    public void printWords(String[] sortedArray) {
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.print(" '" + sortedArray[i] + "' ");
        }
    }

    /**
     * Clean up method for calling each spell checking method in Program
     * @return
     */
    public String[] findNearbyWordsInDictionary() {
        insertMissingLetter();
        removeOneLetter();
        swappedAdjacentChar();
        swappedChar();
        combinedWords();
        String[] resizedArray = Arrays.copyOfRange(matches, 0, numOfMatches);
        return resizedArray;
    }

    /**
     * Inserts potential missing letter typo a-z at every index to see if word is contained in dictionary
     * @return
     */
    public void insertMissingLetter() {
        for(int i = 0; i <= word.length(); i++) {
            for(int j = 0; j < alphabet.length(); j++) {
                String temp = word.substring(0, i) + alphabet.charAt(j) + word.substring(i);

                if(table.contains(temp)) {
                    matches[numOfMatches] = temp;
                    numOfMatches++;
                    resizeIfNecessary();
                }
            }
        }
    }

    /**
     * Removes one letter at every index of string to see if produced strings are contained in table
     * @return
     */
    public void removeOneLetter() {
        for(int i = 0; i < word.length(); i++) {
            StringBuilder sb = new StringBuilder(word);
            String temp = sb.deleteCharAt(i).toString();

            if(table.contains(temp)) {
                matches[numOfMatches] = temp;
                numOfMatches++;
                resizeIfNecessary();
            }
        }
    }

    /**
     * Swaps each adjacent character pair in string to see if it can spot a string produced in hash table
     * @return
     */
    public void  swappedAdjacentChar() {
        for (int i = 0; i < word.length() - 1; i++) {
            int j = i + 1;
            if(j < word.length()) {
                char[] character = word.toCharArray();
                char temp = character[i];
                character[i] = character[j];
                character[j] = temp;
                String swap = String.valueOf(character);
                if (table.contains(swap)) {
                    matches[numOfMatches] = swap;
                    numOfMatches++;
                    resizeIfNecessary();
                }
            }
        }
    }

    /**
     * Swaps each character at every index with a-z to see if it can suggest word contained in hash table
     * @return
     */
    public void swappedChar() {
        for(int i = 0; i < word.length(); i++) {
            for(int j = 0; j < alphabet.length(); j++) {
                char[] character = word.toCharArray();
                character[i] = alphabet.charAt(j);
                String swap = String.valueOf(character);

                if(table.contains(swap)) {
                    matches[numOfMatches] = swap;
                    numOfMatches++;
                    resizeIfNecessary();
                }
            }
        }
    }

    /**
     * Adds space between each index to see if word is contained in hash table
     * @return
     */
    public void combinedWords() {
        for(int i = 0; i < word.length(); i++) {
            String leftSide = word.substring(0, i);
            String rightSide = word.substring(i);

            if(table.contains(leftSide) && table.contains(rightSide)) {
                matches[numOfMatches] = leftSide;
                numOfMatches++;
                matches[numOfMatches] = rightSide;
                numOfMatches++;
                resizeIfNecessary();
            }
        }
    }

    public void resizeIfNecessary() {
        if(numOfMatches > matches.length / 2){
            String[] resizedArray = new String[matches.length * 2];
            for (int i = 0;  i < matches.length; i++) {
                resizedArray[i] = matches[i];
            }
            matches = resizedArray;
        }
    }
}
