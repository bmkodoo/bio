package com.KNS.lesson1;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by kodoo on 16.01.2016.
 */
public class ClumpFinderTest {

    @Test
    public void testFind1() throws Exception {

        Set<String> kmers = ClumpFinder.find(
                "AAAACGTCGAAAAA",
                2, 4, 2);

        assertEquals(kmers.size(), 1);
        assertTrue(kmers.contains("AA"));
    }

    @Test
    public void testFind2() throws Exception {

        Set<String> kmers = ClumpFinder.find(
                "ACGTACGT",
                1, 5, 2);

        assertEquals(kmers.size(), 4);
        assertTrue(kmers.contains("A"));
        assertTrue(kmers.contains("C"));
        assertTrue(kmers.contains("G"));
        assertTrue(kmers.contains("T"));
    }

    @Test
    public void testFind3() throws Exception {

        Set<String> kmers = ClumpFinder.find(
                "CCACGCGGTGTACGCTGCAAAAAGCCTTGCTGAATCAAATAAGGTTCCAGCACATCCTCAATGG" +
                        "TTTCACGTTCTTCGCCAATGGCTGCCGCCAGGTTATCCAGACCTACAGGTCCACCAAAGAACTT" +
                        "ATCGATTACCGCCAGCAACAATTTGCGGTCCATATAATCGAAACCTTCAGCATCGACATTCAAC" +
                        "ATATCCAGCG",
                3, 25, 3);

        assertEquals(kmers.size(), 6);
        assertTrue(kmers.contains("AAA"));
        assertTrue(kmers.contains("CAG"));
        assertTrue(kmers.contains("CAT"));
        assertTrue(kmers.contains("CCA"));
        assertTrue(kmers.contains("GCC"));
        assertTrue(kmers.contains("TTC"));
    }
}