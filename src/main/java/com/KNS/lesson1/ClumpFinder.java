package com.KNS.lesson1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by kodoo on 16.01.2016.
 * Class for Finding Patterns Forming Clumps in a String
 */
public class ClumpFinder {

    /**
     * Given integers L and t, a string Pattern forms an (L, t)-clump inside a (larger) string Genome if there is
     * an interval of Genome of length L in which Pattern appears at least t times.
     * @param genome
     * @param k - k-mer length
     * @param L - Clump length
     * @param t - count of kmers in clump
     * @return All distinct k-mers forming (L, t)-clumps in Genome.
     */
    public static Set<String> find(String genome, int k, int L, int t) {

        Set<String> resultKmers = new HashSet<>();

        Map<String, Integer> count;
        for (int i = 0; i <= genome.length() - L; i++) {
            count = new HashMap<>();

            for (int j = 0; j <= L - k; j++) {
                String kmer = genome.substring(i + j, i + j + k);

                if (count.get(kmer) == null)
                    count.put(kmer, 0);

                count.put(kmer, count.get(kmer) + 1);
            }

            for (String kmer : count.keySet()) {
                if (count.get(kmer) >= t) {
                    resultKmers.add(kmer);
                }
            }
        }

        return resultKmers;
    }

    public static void main(String[] args) {

        StringBuilder genome = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader("Files/genome.fa"))) {
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                genome.append(sCurrentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> kmers = ClumpFinder.find(genome.toString(), 1, 5, 2);
        System.out.println(kmers);
    }

}
