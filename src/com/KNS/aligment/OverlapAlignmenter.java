package com.KNS.aligment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by kodoo on 01.03.2016.
 */
public class OverlapAlignmenter {


    public static void main(String[] args) {
        String protein1;
        String protein2;
        ManhattanGraph graph = null;

        try (BufferedReader in = new BufferedReader(new FileReader("Files/twoProteins.txt"))) {
            protein1 = in.readLine();
            protein2 = in.readLine();

            graph = new ManhattanGraph(
                    protein1,
                    protein2
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
