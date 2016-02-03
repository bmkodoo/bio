package com.KNS.lesson1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Nikolai_Karulin on 1/18/2016.
 *
 * We have previously defined the notion of a Profile-most probable k-mer in a string. We now define a
 * Profile-randomly generated k-mer in a string Text. For each k-mer Pattern in Text, compute the probability
 * Pr(Pattern | Profile), resulting in n = |Text| - k + 1 probabilities (p1, …, pn). These probabilities do not
 * necessarily sum to 1, but we can still form the random number generator Random(p1, …, pn) based on them.
 * GIBBSSAMPLER uses this random number generator to select a Profile-randomly generated k-mer at each step:
 * if the die rolls the number i, then we define the Profile-randomly generated k-mer as the i-th k-mer in Text.
 */
public class GibbsSampler {

    public static List<String> search(int N, int k, List<String> dna) {

        List<String> bestMotifs = new LinkedList<>();
        List<String> motifs = new LinkedList<>();

        final Random random = new Random();
        for (String seq : dna) {
            int dice = random.nextInt(seq.length() - k);
            motifs.add(seq.substring(dice, dice + k));
        }

        bestMotifs.addAll(motifs);
        int bestMotifsScore =
                ProfileGenerator.calcScore(bestMotifs, ProfileGenerator.genConsensusString(ProfileGenerator.generateProfile(bestMotifs)));

        int deselectedSeqIndex = -1;
        for (int j = 0; j < N; j++) {

            int dice;
            do {
                dice = random.nextInt(dna.size() - 1);
            } while (dice == deselectedSeqIndex);

            deselectedSeqIndex = dice;

            double[][] profile = ProfileGenerator.generateProfileExceptOne(motifs, deselectedSeqIndex);

            String selectedMotif = generateProfileRandomlyKmer(dna.get(deselectedSeqIndex), k, profile);
            //String selectedMotif = ProfileMostProbableKmerFinder.find(dna.get(deselectedSeqIndex), k, profile);

            motifs.set(deselectedSeqIndex, selectedMotif);

            int motifScore = ProfileGenerator.calcScore(motifs, ProfileGenerator.genConsensusString(ProfileGenerator.generateProfile(motifs)));
            if (motifScore < bestMotifsScore) {
                bestMotifs.clear();
                bestMotifs.addAll(motifs);
                bestMotifsScore = motifScore;
            }

        }

        return bestMotifs;
    }

    static String generateProfileRandomlyKmer(String seq, int k, double[][] profile) {

        Double[] probes = new Double[seq.length() - k];

//        System.out.printf("Diapasons: ");
        for (int i = 0; i < seq.length() - k; i++) {
            probes[i] = ProfileMostProbableKmerFinder.checkProbability(seq.substring(i, i + k), profile);
            probes[i] *= 100000;

//            System.out.printf("%d:(%.2f)", i, probes[i]);

            if (i != 0)
                probes[i] += probes[i - 1];

            //System.out.printf("%.2f|", probes[i]);
        }

        double randomStrike = new Random().nextDouble() * probes[probes.length - 1];

        int selectedMotif = -1;
        for (int i = 0; i < probes.length; i++) {
            if (randomStrike < probes[i]) {
                selectedMotif = i;
                break;
            }
        }

//        System.out.printf(" striked at %d(%1.2f)\n", selectedMotif, probes[selectedMotif]);

        return seq.substring(selectedMotif, selectedMotif + k);
    }

    public static void main(String[] args) {

        List<String> genome = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("Files/genome.fa"))) {
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                genome.add(sCurrentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        List<String> motifsConon = new LinkedList<>();
//        motifsConon.add("TCTCGGGG");
//        motifsConon.add("CCAAGGTG");
//        motifsConon.add("TACAGGCG");
//        motifsConon.add("TTCAGGTG");
//        motifsConon.add("TCCACGTG");
//        System.out.printf("Goal score: %d\n", calcScore(motifsConon, genConsensusString(generateProfile(motifsConon))));

        int recordScore = Integer.MAX_VALUE;
        List<String> recordMotifs = null;

        for (int i = 0; i < 200; i++) {
            List<String> bestMotifs = search(2000, 15, genome);
            int score = ProfileGenerator.calcScore(bestMotifs, ProfileGenerator.genConsensusString(ProfileGenerator.generateProfile(bestMotifs)));

            if (score < recordScore) {
                recordScore = score;
                recordMotifs = bestMotifs;
            }
        }

        System.out.printf("Best score: %d\n", recordScore);
        for (String motif : recordMotifs)
            System.out.printf("%s\n", motif);
    }
}
