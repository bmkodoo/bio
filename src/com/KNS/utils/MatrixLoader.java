package com.KNS.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by kodoo on 07.04.2016.
 */
public class MatrixLoader {

    public static int[][] loadFromFile(String fileName) {
        try (FileInputStream in = new FileInputStream(new File(fileName))) {
            Scanner scanner = new Scanner(in);
            final int size = scanner.nextInt();
            int[][] loadingMatrix = new int[size][size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    loadingMatrix[y][x] = scanner.nextInt();
                }
            }
            return loadingMatrix;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
