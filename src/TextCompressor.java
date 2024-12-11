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
        String input = BinaryStdIn.readString();

        int CODE_BITS = 12;

        TST codes = new TST();
        int codeIndex = 257;
        int i = 0;
        String prefix = input.charAt(0) + "";

        while (i < input.length()) {
            String longestPrefix = codes.getLongestPrefix(prefix+input.charAt(i+1));
            int lookaheadCode = codes.lookup(prefix+input.charAt(i+1));
            // If the lookahead prefix doesn't appear in the codes
            if (longestPrefix.isEmpty()) {
                BinaryStdOut.write(prefix, CODE_BITS);
                codes.insert(prefix+input.charAt(++i), codeIndex++);
            } else {
                prefix += input.charAt(++i);
                BinaryStdOut.write(prefix, CODE_BITS);
                codes.insert(prefix+input.charAt(++i), codeIndex++);
            }
            int location = codes.lookup(prefix);





            if (prefix.length() == 1) {
                BinaryStdOut.write(prefix, CODE_BITS);
                codes.insert(prefix+input.charAt(++i), codeIndex++);
            }
            else {
                if (location != -1) {
                    BinaryStdOut.write(location, CODE_BITS);
                    codes.insert(prefix+input.charAt(++i), codeIndex++);
                }
            }
        }

        // TODO: Complete the compress() method

        BinaryStdOut.close();
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
