package com.KNS.utils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Created by kodoo on 06.04.2016.
 */
public class MatrixCalculator {

    public static double[][] calculateNeighborJoinMatrix(double[][] distanceMatrix) {
        final int size = distanceMatrix.length;
        double[][] neighborJoiningMatrix = new double[size][size];

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

    public static double totalDistance(double[][] distanceMatrix, int row) {
        return DoubleStream.of(distanceMatrix[row]).sum();
    }

    public static Point getMinimalElementPosition(double[][] matrix) {
        final int size = matrix.length;

        Point position = new Point();

        double record = Integer.MAX_VALUE;
        int xMax = size;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++, xMax--) {
                if (x == y)
                    continue;

                if (matrix[y][x] <= record) {
                    record = matrix[y][x];
                    position.setLocation(x, y);
                }
            }
        }

        return position;
    }

    public static double calculateDelta(double[][] distanceMatrix, int x, int y) {
        return (totalDistance(distanceMatrix, x) - totalDistance(distanceMatrix, y)) / (distanceMatrix.length - 2);
    }

    public static double[][] addMColumn(double[][] distanceMatrix, int i, int j) {
        final int size = distanceMatrix.length;

        double[][] matrixWithM = new double[size + 1][size + 1];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                matrixWithM[y + 1][x + 1] = distanceMatrix[y][x];
            }
        }

        for (int k = 0; k < size; k++) {
            double distanceFromKtoM = (distanceMatrix[k][i] + distanceMatrix[k][j] - distanceMatrix[j][i]) / 2;
            matrixWithM[k][0] = distanceFromKtoM;
            matrixWithM[0][k] = distanceFromKtoM;
        }

        return matrixWithM;
    }

    public static double[][] removeColumnAndRow(double[][] matrix, int pos1, int pos2) {
        final int size = matrix.length;
        double[][] matrixAfterRemoving = new double[size - 2][size - 2];
        int IInNewMatrix = 0;
        for (int i = 0; i < size; i++) {
            if (i == pos1 || i == pos2)
                continue;
            int JInNewMatrix = 0;
            for (int j = 0; j < size; j++) {
                if (j == pos1 || j == pos2)
                    continue;

                matrixAfterRemoving[JInNewMatrix][IInNewMatrix] = matrix[j][i];
                JInNewMatrix++;
            }
            IInNewMatrix++;
        }

        return matrixAfterRemoving;
    }

    public static String matrixToString(double[][] matrix) {
        final int size = matrix.length;
        StringBuilder str = new StringBuilder();
        Formatter formatter = new Formatter(str);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                formatter.format("%3.1f ", matrix[y][x]);
            }
            formatter.format("\n");
        }

        return str.toString();
    }

}