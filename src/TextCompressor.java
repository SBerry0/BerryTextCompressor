/******************************************************************************
 *  Compilation:  javac TextCompressor.java
 *  Execution:    java TextCompressor - < input.txt   (compress)
 *  Execution:    java TextCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   abra.txt
 *                jabberwocky.txt
 *                shakespeare.txt
 *                virus.txt
 *
 *  % java DumpBinary 0 < abra.txt
 *  136 bits
 *
 *  % java TextCompressor - < abra.txt | java DumpBinary 0
 *  104 bits    (when using 8-bit codes)
 *
 *  % java DumpBinary 0 < alice.txt
 *  1104064 bits
 *  % java TextCompressor - < alice.txt | java DumpBinary 0
 *  480760 bits
 *  = 43.54% compression ratio!
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, YOUR NAME HERE
 */
public class TextCompressor {
    private ArrayList<String> words;

    public TextCompressor() {
        this.words = new ArrayList<>();
    }

    private static void compress() throws IOException {
        /**
         * ok here's the plan:
         * z = number of bits that will be used for the codes
         *Compression:
         * 1. Go through the input string and search for the most common substrings in it
         *         First I'm going to check for most common of a certain length like 3 or 4
         *         Then later I can make it search for the lengths from 2 to like 10
         *         and calculate the top x of them by multiplying their frequency by their length
         * 2. Assign the top z-2 of the frequent words to a z length bit in a map, then write it as a header (saving this for later, gonna get later steps to work first
         * 3. Start writing each character by character with 8 bits, leave something like 1111 1111 as an
         *      escape character that will be written when a substring that is in the map is detected
         * 4. When finished writing out a code, write an escape character like 1111111111
         *
         * Expansion:
         * 1. Read in the map into an object
         * 2. Start with assuming it's a real word until you hit escape character, then write out codes based on the map
         */

        Scanner s = new Scanner(new File("common.txt"));
        s.useDelimiter("\\n");
        String commonWordsString = "";
        while (s.hasNext()) {
            commonWordsString += s.next();
            commonWordsString += "\n";
        }
        String[] commonWords = commonWordsString.split("\n");
        System.out.println(commonWords.length);

        String input = BinaryStdIn.readString();

        int pos = 0;

        for (int i = 0; i < input.length() - 10; i++) {
            for (int j = 0; j < 10; j++) {
                int code = getSubstringCode(commonWords, input.substring(i, j));
                if (code != commonWords.length) {

                }
            }
        }

        for (int i = 0; i < commonWords.length; i++) {

        }

        // TODO: Complete the compress() method

        BinaryStdOut.close();
    }

    private static int getSubstringCode(String[] commonWords, String word) {
        // wow this is gonna be slow
        for (int i = 0; i < commonWords.length; i++) {
            if (commonWords[i].equals(word)) {
                return i;
            }
        }
        return commonWords.length;
    }

    private static void expand() {

        // TODO: Complete the expand() method

        BinaryStdOut.close();
    }

    public static void main(String[] args) throws IOException {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
