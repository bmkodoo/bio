package com.KNS.contigs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static com.KNS.contigs.Lisp.head;
import static com.KNS.contigs.Lisp.tail;

/**
 * Compute the Score of a Linear Peptide
 *
 * Compute the score of a linear peptide with respect to a spectrum.
 * Given: An amino acid string Peptide and a collection of integers LinearSpectrum.
 * Return: The linear score of Peptide against Spectrum, LinearScore(Peptide, Spectrum).
 */
public class Spectrum {

    private static HashMap<Character, Integer> masssTable;

    public static void init() {
        masssTable = new HashMap<>();

        masssTable.put('G', 57);
        masssTable.put('A', 71);
        masssTable.put('S', 87);
        masssTable.put('P', 97);
        masssTable.put('V', 99);
        masssTable.put('T', 101);
        masssTable.put('C', 103);
        masssTable.put('I', 113);
        masssTable.put('L', 113);
        masssTable.put('N', 114);
        masssTable.put('D', 115);
        masssTable.put('K', 128);
        masssTable.put('Q', 128);
        masssTable.put('E', 129);
        masssTable.put('M', 131);
        masssTable.put('H', 137);
        masssTable.put('F', 147);
        masssTable.put('R', 156);
        masssTable.put('Y', 163);
        masssTable.put('W', 186);
    }

    public static int calcMass(String amino) {

        return amino.chars()
                .map((ch) -> masssTable.get(Character.toChars(ch)[0]))
                .sum();
    }

    public static List<Integer> generate(String acid) {
        List<Integer> spectrum = new LinkedList<>();
        spectrum.add(0);
        spectrum.add(calcMass(acid));

        for (int partLength = 1; partLength < acid.length(); partLength++) {
            for (int pos = 0; pos <= acid.length() - partLength; pos++) {
                String substring = acid.substring(pos, pos + partLength);
                spectrum.add(calcMass(substring));
            }
        }

        return spectrum;
    }

    public static int score(List<Integer> theoretic, List<Integer> practice) {

        if (theoretic.size() == 0)
            return 0;

        if (practice.size() == 0)
            return 0;

        if (head(theoretic) > head(practice))
            return score(theoretic, tail(practice));

        if (head(theoretic) < head(practice))
            return score(tail(theoretic), practice);

        return 1 + score(tail(theoretic), tail(practice));
    }

    public static void main(String[] args) {
        init();

        List<Integer> specter = new ArrayList<>();
        try (Scanner in = new Scanner(new BufferedReader(new FileReader("Files/spectrum")))) {
            while (in.hasNextInt()) {
                specter.add(in.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Integer> theor = generate("QGEELLWNCRTGDDYSGGFMLVTYEAFRPMEDKCHHR")
                .stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.printf("Score: %d\n", score(theor, specter));
    }
}
