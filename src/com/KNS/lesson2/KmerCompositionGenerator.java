package com.KNS.lesson2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Created by Nikolai_Karulin on 2/3/2016.
 */
public class KmerCompositionGenerator {

    /**
     * Given a string Text, its k-mer composition Compositionk(Text) is the collection of all k-mer substrings
     * of Text (including repeated k-mers).
     * @param text
     * @param k
     * @return Compositionk(Text) (the k-mers can be provided in any order).
     */
    public static Collection<String> generate(String text, int k) {
        TreeSet<String> composition = new TreeSet<>();
        for (int i = 0; i <= text.length() - k; i++) {
            composition.add(text.substring(i, i + k));
        }

        return composition;
    }

     public static <T> void printCollection(Collection<T> colect) {
         for (T elem : colect) {
             System.out.printf("%s\n", elem);
         }
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

        printCollection(generate(genome.toString(), 50));
    }
}
