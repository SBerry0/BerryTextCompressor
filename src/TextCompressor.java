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


/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Sohum Berry
 */
public class TextCompressor {
    public static final int CODE_BITS = 12,
                            EOF = 256;

    private static void compress() {
        String input = BinaryStdIn.readString();

        TST codes = new TST();

        int codeIndex = EOF+1;
        int i = 0;
        String prefix;

        while (i < input.length()) {
            // Find longest prefix
            prefix = codes.getLongestPrefix(input, i);
            // Find associated code with prefix
            int code = codes.lookup(prefix);
            // Write out the code
            BinaryStdOut.write(code, CODE_BITS);
            // Increment start index by the length of the prefix
            i += prefix.length();
            // Check to stay in bounds
            if (i >= input.length())
                break;
            // Insert new code into the TST then increment codeIndex value
            codes.insert(prefix+input.charAt(i), codeIndex++);
        }
        // Write end of file and close
        BinaryStdOut.write(EOF, CODE_BITS);
        BinaryStdOut.close();
    }

    private static void expand() {
        // Like decode and codes...get it?
        String[] decodes = new String[(int) Math.pow(2, CODE_BITS)];
        for (int i = 32; i < 256; i++) {
            decodes[i] = (char) (i) + "";
        }
        int value = BinaryStdIn.readInt(CODE_BITS);
        int codeIndex = EOF+1;

        while (value != EOF) {
            BinaryStdOut.write(decodes[value]);

            int newVal = BinaryStdIn.readInt(CODE_BITS);
            String s = decodes[newVal];
            if (s == null) {
                break;
            }
            decodes[codeIndex++] = decodes[value] + s.charAt(0);

            value = newVal;
        }

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
