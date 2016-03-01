package com.KNS.aligment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by kodoo on 01.03.2016.
 */
public class OverlapAlignmenter {

    String protein1;
    String protein2;

    public static void main(String[] args) {
        OverlapAlignmenter alignmenter = new OverlapAlignmenter();
        alignmenter.loadProteins("Files/twoProteins.txt");
        ManhattanGraph graph = new ManhattanGraph(
                alignmenter.getProtein1().length(),
                alignmenter.getProtein2().length()
        );
    }

    void loadProteins(String fileName) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            protein1 = in.readLine();
            protein2 = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProtein1() {
        return protein1;
    }

    public String getProtein2() {
        return protein2;
    }
}
