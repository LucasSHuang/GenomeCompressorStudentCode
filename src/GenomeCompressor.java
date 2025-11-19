/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */

    public static final int BITS_PER_CHAR = 2;
    public static void compress() {

        // Created a map to keep track of values for all 4 DNA chars
        short[] map = new short[25];
        map[0] = 0b00;
        map['C' - 'A'] = 0b01;
        map['G' - 'A'] = 0b10;
        map['T' - 'A'] = 0b11;

        String s = BinaryStdIn.readString();
        int n = s.length();
        // Write out the length of the string for expansion
        BinaryStdOut.write(n);

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // Compress into binary value
            BinaryStdOut.write(map[c - 'A'], BITS_PER_CHAR);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {

        // Map to keep track of char value at binary value
        char[] map = new char[4];
        map[0b00] = 'A';
        map[0b01] = 'C';
        map[0b10] = 'G';
        map[0b11] = 'T';

        // Gets length of string to know how long to go
        int n = BinaryStdIn.readInt();
        for (int i = 0; i < n; i++) {
            // Expand back into DNA
            int result = BinaryStdIn.readInt(BITS_PER_CHAR);
            BinaryStdOut.write(map[result]);
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}