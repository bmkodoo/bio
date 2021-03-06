package com.KNS.lesson1;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nikolai_Karulin on 1/18/2016.
 */
public class ProfileGenerator {

    public static String genConsensusString(double[][] profile) {

        if (profile == null)
            return "";

        StringBuilder consensusString = new StringBuilder();

        for (int pos = 0; pos < profile[0].length; pos++) {
            double scoreRecord = 0;
            int baseRecord = -1;
            for (int baseType = 0; baseType < 4; baseType++) {
                if (profile[baseType][pos] > scoreRecord) {
                    scoreRecord = profile[baseType][pos];
                    baseRecord = baseType;
                }
            }

            switch (baseRecord) {
                case 0: consensusString.append('A'); break;
                case 1: consensusString.append('C'); break;
                case 2: consensusString.append('G'); break;
                case 3: consensusString.append('T'); break;
                default: consensusString.append('?');
            }

        }

        return consensusString.toString();
    }

    public static double[][] generateProfileExceptOne(List<String> motifs, int oneIndex) {
        List<String> exceptOneList = new LinkedList<>();
        exceptOneList.addAll(motifs);
        exceptOneList.subList(oneIndex, oneIndex + 1).clear();

        return generateProfile(exceptOneList);
    }

    public static double[][] generateProfile(List<String> motifs) {

        if (motifs.size() < 1)
            return null;

        int motifLength = motifs.iterator().next().length();
        double[][]  count = new double[4][motifLength];
        double[]    sum = new double[motifLength];
        double[][]  profile = new double[4][motifLength];

        for (int i = 0; i < motifLength; i++) {
            for (int j = 0; j < 4; j++) {
                count[j][i] = 1;
                profile[j][i] = 0;
                sum[j] = 4;
            }

        }

        // Fill count matrix
        for (String motif : motifs) {
            for (int pos = 0; pos < motifLength; pos++) {
                char base = motif.charAt(pos);

                switch (base) {
                    case 'A':
                        count[0][pos]++;
                        break;
                    case 'C':
                        count[1][pos]++;
                        break;
                    case 'G':
                        count[2][pos]++;
                        break;
                    case 'T':
                        count[3][pos]++;
                }

                sum[pos]++;
            }
        }

        //System.out.printf("Count:\n");
        //printProfile(count, System.out);
        //System.out.printf("Sum: %s\n", Arrays.toString(sum));

        //Fill profile matrix
        for (int pos = 0; pos < motifLength; pos++) {
            for (int i = 0; i < 4; i++)
                profile[i][pos] = count[i][pos] / sum[i];
        }

        //System.out.printf("Profile:\n");
        //printProfile(profile, System.out);

        return profile;
    }

    public static int calcScore(Collection<String> motifs, String consensusString) {

        if (motifs.size() == 0)
            return Integer.MAX_VALUE;

        int score = 0;
        for (String motif : motifs) {
            for (int i = 0; i < motif.length(); i++) {
                if (motif.charAt(i) != consensusString.charAt(i))
                    score++;
            }
        }

        return score;
    }

    public static void printProfile(double[][] profile, PrintStream out) {

        int motifLength = profile[0].length;

        System.out.printf("A: ");
        for (int i = 0; i < motifLength; i++) {
            System.out.printf(" %1.3f", profile[0][i]);
        }

        System.out.printf("\nC: ");
        for (int i = 0; i < motifLength; i++) {
            System.out.printf(" %1.3f", profile[1][i]);
        }

        System.out.printf("\nG: ");
        for (int i = 0; i < motifLength; i++) {
            System.out.printf(" %1.3f", profile[2][i]);
        }

        System.out.printf("\nT: ");
        for (int i = 0; i < motifLength; i++) {
            System.out.printf(" %1.3f", profile[3][i]);
        }

        System.out.println("");
    }

    public static void main(String[] args) {

        List<String> motifs = new LinkedList<>();
        motifs.add("ACGTATGT");
        motifs.add("CCGGATGT");
        motifs.add("GCGGATGT");
        motifs.add("TCGGACGT");

        double[][] profile = ProfileGenerator.generateProfile(motifs);
        System.out.printf("Profile: \n%s\n", Arrays.deepToString(profile));

        String cons = genConsensusString(profile);
        System.out.printf("Consensus sting: %s\n", cons);

        int score = calcScore(motifs, cons);
        System.out.printf("Score: %d\n", score);
    }
}
