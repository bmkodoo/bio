package com.KNS;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Nikolai_Karulin on 1/18/2016.
 */
public class ProfileGenerator {

    public static String genConsensusString(double[][] profile) {

        StringBuilder consensusString = new StringBuilder();

        for (int pos = 0; pos < profile[0].length; pos++) {
            double scoreRecord = 0;
            int baseRecord = -1;
            for (int baseType = 0; baseType < profile.length; baseType++) {
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

    public static double[][] generateProfile(Collection<String> motifs) {

        if (motifs.size() < 1)
            return null;

        int motifLength = motifs.iterator().next().length();
        int[][]     count = new int[4][motifLength];
        double[][]  profile = new double[4][motifLength];

        for (int i = 0; i < motifLength; i++) {
            for (int j = 0; j < 4; j++) {
                count[j][i] = 1;
                profile[j][i] = 0;
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
            }
        }

        int motifsCount = motifs.size() + 4; // Laplace's Rule

        //Fill profile matrix
        for (int pos = 0; pos < motifLength; pos++) {
            for (int i = 0; i < 4; i++)
                profile[i][pos] = (double) count[i][pos]/motifsCount;
        }

        return profile;
    }

    public static void main(String[] args) {

        Collection<String> motifs = new LinkedList<>();
        motifs.add("ACGTATGT");
        motifs.add("CCGGATGT");
        motifs.add("GCGGATGT");
        motifs.add("TCGGACGT");

        double[][] profile = ProfileGenerator.generateProfile(motifs);
        System.out.printf("Profile: \n%s\n", Arrays.deepToString(profile));

        String cons = genConsensusString(profile);
        System.out.printf("Consensus sting: %s\n", cons);
    }
}
