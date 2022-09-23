import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class HashTable {
    int wordsInTable = 0;
    int tableSize;

    LinkedList<KeyValuePair>[] table;

    public HashTable(int tableSize) {
        this.tableSize = tableSize;
        table = new LinkedList[tableSize];
    }

    /**
     * Takes a key as input, creates a has for the key.
     * @param key
     * @return
     */
    private int hash(String key) {
        int hash = 1;
        for(int i = 0; i < key.length(); i++) {
            byte temp = (byte) key.charAt(i);
            int OPTIMUS_PRIME = 7723;

            hash *= (i + temp + OPTIMUS_PRIME);
            hash = hash % tableSize + 1;
        }
        return hash - 1;
        //FNV-1
//        int rv = 0x811c9dc5;
//        final int len = key.length();
//        for(int i = 0; i < len; i++) {
//            rv ^= key.charAt(i);
//            rv *= 0x01000193;
//        }
//        return Math.abs(rv) % tableSize;
    }

    /**
     * Checks to see whether a key is contained within the hash table. Returns true if it is, else false.
     * @param key
     * @return
     */
    public boolean contains(String key) {
        int keyHash = hash(key);
        if (table[keyHash] == null) return false;

        for(int i = 0; i < table[keyHash].size(); i++) {
            if(key.equals(table[keyHash].get(i).key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a key into the table
     * @param key
     */
    public void add(String key) {
        int keyHash = hash(key);
        if(table[keyHash] == null) {
            table[keyHash] = new LinkedList<KeyValuePair>();
        }
        if(this.contains(key)) {
            return;
        }
        table[keyHash].add(new KeyValuePair(key, true));
        wordsInTable++;
    }

    /**
     * Removes key from table
     * @param key
     */
    public void remove(String key) {
        int keyHash = hash(key);
        if(this.contains(key)) {
            for (int i = table[keyHash].size() - 1; i >= 0; i--) {
                if (table[keyHash].get(i).key.equals(key)) {
                    table[keyHash].remove(i);
                    wordsInTable--;
                }
            }
        }
    }

    /**
     * Returns the number of strings within a table
     * @return
     */
    public int size() {
        return wordsInTable;
    }

    /**
     * Helper function that uses derived mathematical formula for finding expected number of collisions
     * @param n
     * @param m
     * @return
     */
    public static int expectedCollisions(double n, double m) {
        double expectedCollisions = n - m * (1 - Math.pow(((m - 1) / m), n));
        return (int) expectedCollisions;
    }

    /**
     * Returns number of collisions of hash table
     * @return
     */
    public int getCollisions() {
        int numCollisions = 0;
        for(int i = 0; i < table.length; i++) {
            if(table[i] == null) {
                numCollisions += 0;
            } else {
                numCollisions += table[i].size() - 1;
            }
        }
        return numCollisions;
    }

    /**
     * Generates adjusted table size give number of strings from a file
     * @param file
     * @return
     * @throws IOException
     */
    public static int getTableSize(String file) throws IOException {
        Path path = Paths.get(file);
        int numOfLines = 0;
        int estimatedSize;
        int adjustedSize;

        try {
            numOfLines = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        estimatedSize = (int) (numOfLines * 1.3);
        adjustedSize = nextHighestPrime(estimatedSize);

        return adjustedSize;
    }

    /**
     * Returns boolean if inputed number is a prime number
     * @param number
     * @return
     */
    private static boolean isPrime(int number) {
        for(int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Takes inputed number and finds the next highest prime, if not a prime already.
     * @param numberToPrime
     * @return
     */
    private static int nextHighestPrime(int numberToPrime) {
        int prime = numberToPrime;
        boolean nextPrime = false;

        while(!nextPrime) {
            if(!isPrime(prime)) {
                prime++;
            } else {
                nextPrime = true;
            }
        }
        return prime;
    }
}


