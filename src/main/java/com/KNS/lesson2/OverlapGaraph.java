package com.KNS.lesson2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Nikolai_Karulin on 2/3/2016.
 */
public class OverlapGaraph {

    Map<String, String> graph;

    public OverlapGaraph(Collection<String> kmers) {
        graph = new TreeMap<>();
        Map<String, String> prefixes = new HashMap<>();

        for (String kmer : kmers) {
            prefixes.put(kmer.substring(0, kmer.length() - 1), kmer);
        }

        for (String kmer : kmers) {
            graph.put(kmer, prefixes.get(kmer.substring(1, kmer.length())));
        }
    }

    public void printGraph() {
        for (String nodeFrom : graph.keySet()) {
            String nodeTo;
            if ((nodeTo = graph.get(nodeFrom)) != null) {
                System.out.printf("\n%s -> %s", nodeFrom, nodeTo);
            }
        }
    }

    public static void main(String[] args) {
        List<String> kmers = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("Files/genome.fa"))) {
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                kmers.add(sCurrentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OverlapGaraph gr = new OverlapGaraph(kmers);
        gr.printGraph();
    }
}
