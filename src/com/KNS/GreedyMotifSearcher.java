package com.KNS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.KNS.ProfileGenerator.*;

/**
 * Created by Nikolai_Karulin on 1/18/2016.
 */
public class GreedyMotifSearcher {

    public static List<String> search(int k, Collection<String> dna) {

        List<String> bestMitifs = new LinkedList<>();
        int bestScore = Integer.MAX_VALUE;
        Iterator<String> dnaIt = dna.iterator();
        String firstSeq = dnaIt.next();

        for (int i = 0; i <= firstSeq.length() - k; i++) {
            dnaIt = dna.iterator();

            List<String> motifs = new LinkedList<>();
            motifs.add(dnaIt.next().substring(i, i + k));
            System.out.printf("Start, motifs: %s\n", motifs);

            while (dnaIt.hasNext()) {
                motifs.add(ProfileMostProbableKmerFinder.find(
                        dnaIt.next(), k, generateProfile(motifs)
                ));
                System.out.printf("add, motifs: %s\n", motifs);
            }

            int score = calcScore(motifs, genConsensusString(generateProfile(motifs)));
            if (score < bestScore) {
                bestMitifs = motifs;
                bestScore = score;
                System.out.printf("Best(%d)!: %s\n", bestScore, bestMitifs);
            }

        }

        return bestMitifs;
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

        Collection<String> bestMatifs = GreedyMotifSearcher.search(3, genome);
        System.out.printf("Best motifs: %s\n", bestMatifs);
    }
}
