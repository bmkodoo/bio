package com.KNS.contigs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nikolai_Karulin on 2/10/2016.
 */
public class Cyclospectrum {

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

    static class Cyclic {
        String amino;

        Cyclic(String amino) {
            this.amino = amino;
        }

        String substring(int pos, int length) {

            if (pos + length > amino.length()) {
                return amino.substring(pos, amino.length()).concat(amino.substring(0, length - (amino.length() - pos)));
            }

            return amino.substring(pos, pos + length);
        }

        int length() { return amino.length(); }

        @Override
        public String toString() {
            return amino;
        }
    }

    public static int calcMass(String amino) {

        return amino.chars()
                .map((ch) -> masssTable.get(Character.toChars(ch)[0]))
                .sum();
    }

    public static List<Integer> generate(Cyclic acid) {

        List<Integer> spectrum = new LinkedList<>();
        spectrum.add(0);
        spectrum.add(calcMass(acid.toString()));

        for (int partLength = 1; partLength < acid.length(); partLength++) {
            for (int pos = 0; pos < acid.length(); pos++) {
                String substring = acid.substring(pos, partLength);
                System.out.printf("%s\n", substring);
                spectrum.add(calcMass(substring));
            }
        }

        return spectrum;
    }

    public static void main(String[] args) {
        init();
        System.out.printf("substring: %s\n", (new Cyclic("ABCD")).substring(3, 3));
        generate(new Cyclic("NCMNYEAMITRA")).stream().sorted().forEach((mass) -> System.out.printf("%d ", mass));
    }
}
