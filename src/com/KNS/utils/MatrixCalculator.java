package com.KNS.utils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by kodoo on 06.04.2016.
 */
public class MatrixCalculator {

    public static int[][] calculateNeighborJoinMatrix(int[][] distanceMatrix) {
        final int size = distanceMatrix.length;
        int[][] neighborJoiningMatrix = new int[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (x == y)
                    continue;

                neighborJoiningMatrix[y][x] = (size - 2) * distanceMatrix[y][x]
                        - totalDistance(distanceMatrix, x)
                        - totalDistance(distanceMatrix, y);
            }
        }
        
        return neighborJoiningMatrix;
    }

    public static int totalDistance(int[][] distanceMatrix, int row) {
        return IntStream.of(distanceMatrix[row]).sum();
    }

    public static Point getMinimalElementPosition(int[][] matrix) {
        final int size = matrix.length;

        Point position = new Point();

        int record = Integer.MAX_VALUE;
        int xMax = size;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < xMax; x++, xMax--) {
                if (x == y)
                    continue;

                if (matrix[y][x] < record) {
                    record = matrix[y][x];
                    position.setLocation(x, y);
                }
            }
        }

        return position;
    }

    public static int calculateDelta(int[][] distanceMatrix, int x, int y) {
        return (totalDistance(distanceMatrix, x) - totalDistance(distanceMatrix, y)) / (distanceMatrix.length - 2);
    }

    public static String matrixToString(int[][] matrix) {
        final int size = matrix.length;
        StringBuilder str = new StringBuilder();
        Formatter formatter = new Formatter(str);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                formatter.format("%3d ", matrix[y][x]);
            }
            formatter.format("\n");
        }

        return str.toString();
    }

}