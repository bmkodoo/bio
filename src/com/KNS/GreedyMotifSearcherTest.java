package com.KNS;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nikolai_Karulin on 1/18/2016.
 */
public class GreedyMotifSearcherTest {

    @Test
    public void testSearch1() throws Exception {

        List<String> genome = new LinkedList<>();
        genome.add("GGCGTTCAGGCA");
        genome.add("AAGAATCAGTCA");
        genome.add("CAAGGAGTTCGC");
        genome.add("CACGTCAATCAC");
        genome.add("CAATAATATTCG");

        List<String> bestMatifs = GreedyMotifSearcher.search(3, genome);

        assertEquals(bestMatifs.size(), 5);
        assertTrue(bestMatifs.get(0).equals("CAG"));
        assertTrue(bestMatifs.get(1).equals("CAG"));
        assertTrue(bestMatifs.get(2).equals("CAA"));
        assertTrue(bestMatifs.get(3).equals("CAA"));
        assertTrue(bestMatifs.get(4).equals("CAA"));
    }

    @Test
    public void testSearch2() throws Exception {

        List<String> genome = new LinkedList<>();
        genome.add("GCCCAA");
        genome.add("GGCCTG");
        genome.add("AACCTA");
        genome.add("TTCCTT");

        List<String> bestMatifs = GreedyMotifSearcher.search(3, genome);

        assertEquals(bestMatifs.size(), 4);
        assertEquals(bestMatifs.get(0), "GCC");
        assertEquals(bestMatifs.get(1), "GCC");
        assertEquals(bestMatifs.get(2), "AAC");
        assertEquals(bestMatifs.get(3), "TTC");
    }

    @Test
    public void testSearch3() throws Exception {

        List<String> genome = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("Files/g1.fa"))) {
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                genome.add(sCurrentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> bestMatifs = GreedyMotifSearcher.search(5, genome);

        assertEquals(bestMatifs.size(), 8);
        assertEquals(bestMatifs.get(0), "GAGGC");
        assertEquals(bestMatifs.get(1), "TCATC");
        assertEquals(bestMatifs.get(2), "TCGGC");
        assertEquals(bestMatifs.get(3), "GAGTC");
        assertEquals(bestMatifs.get(4), "GCAGC");
        assertEquals(bestMatifs.get(5), "GCGGC");
        assertEquals(bestMatifs.get(6), "GCGGC");
        assertEquals(bestMatifs.get(7), "GCATC");

    }

    @Test
    public void testSearch4() throws Exception {

        List<String> genome = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new FileReader("Files/g2.fa"))) {
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                genome.add(sCurrentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> bestMatifs = GreedyMotifSearcher.search(6, genome);

        assertEquals(bestMatifs.size(), 5);
        assertEquals(bestMatifs.get(0), "GTGCGT");
        assertEquals(bestMatifs.get(1), "GTGCGT");
        assertEquals(bestMatifs.get(2), "GCGCCA");
        assertEquals(bestMatifs.get(3), "GTGCCA");
        assertEquals(bestMatifs.get(4), "GCGCCA");

    }

    @Test
    public void testSearch5() throws Exception {

        List<String> genome = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new FileReader("Files/g3.fa"))) {
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                genome.add(sCurrentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> bestMatifs = GreedyMotifSearcher.search(5, genome);

        assertEquals(bestMatifs.size(), 8);
        assertEquals(bestMatifs.get(0), "GCAGC");
        assertEquals(bestMatifs.get(1), "TCATT");
        assertEquals(bestMatifs.get(2), "GGAGT");
        assertEquals(bestMatifs.get(3), "TCATC");
        assertEquals(bestMatifs.get(4), "GCATC");
        assertEquals(bestMatifs.get(5), "GCATC");
        assertEquals(bestMatifs.get(6), "GGTAT");
        assertEquals(bestMatifs.get(7), "GCAAC");

    }

}