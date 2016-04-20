package com.KNS.evo;

import com.KNS.utils.MatrixLoader;
import com.KNS.utils.MatrixCalculator;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.awt.*;

/**
 * Created by kodoo on 06.04.2016.
 */
public class NeighborJoiner {

    private static int nextVertex = 0;

    public static WeightedGraph<Integer, DefaultWeightedEdge> join(double[][] distanceMatrix) {
        final int size = distanceMatrix.length;

        System.out.printf("distanceMatrix:\n%s", MatrixCalculator.matrixToString(distanceMatrix));

        if (size == 2) {
            return createSimpleTree(distanceMatrix[1][1]);
        }

        double[][] neighborJoiningMatrix = MatrixCalculator.calculateNeighborJoinMatrix(distanceMatrix);
        System.out.printf("neighborJoiningMatrix:\n%s\n", MatrixCalculator.matrixToString(neighborJoiningMatrix));

        Point minimalElementPosition = MatrixCalculator.getMinimalElementPosition(neighborJoiningMatrix);
        System.out.printf("minimalElementPosition: %s\n", minimalElementPosition);

        double delta = MatrixCalculator.calculateDelta(
                distanceMatrix,
                minimalElementPosition.x,
                minimalElementPosition.y);
        System.out.printf("Delta: %f\n", delta);

        double limbLengthA = 0.5 * (distanceMatrix[minimalElementPosition.y][minimalElementPosition.x] + delta);
        System.out.printf("limbLengthA = %f\n", limbLengthA);

        double limbLengthB = 0.5 * (distanceMatrix[minimalElementPosition.y][minimalElementPosition.x] - delta);
        System.out.printf("limbLengthB = %f\n", limbLengthB);

        double[][] matrixAfterRemoving = MatrixCalculator.removeColumnAndRow(
                distanceMatrix,
                minimalElementPosition.x,
                minimalElementPosition.y);
        System.out.printf("After removing:\n%s", MatrixCalculator.matrixToString(matrixAfterRemoving));

        double[][] matrixWithM = MatrixCalculator.addMColumn(
                matrixAfterRemoving,
                minimalElementPosition.x - 2,
                minimalElementPosition.y - 2);
        System.out.printf("matrix with M column added:\n%s", MatrixCalculator.matrixToString(matrixWithM));


        WeightedGraph<Integer, DefaultWeightedEdge> resultTree = join(matrixWithM);

        return null;
    }

    private static WeightedGraph<Integer, DefaultWeightedEdge> createSimpleTree(double distance) {
        WeightedGraph<Integer, DefaultWeightedEdge> tree = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        int newVertexA = nextVertex();
        int newVertexB = nextVertex();
        tree.addVertex(newVertexA);
        tree.addVertex(newVertexB);
        tree.setEdgeWeight(
                tree.addEdge(newVertexA, newVertexB),
                distance
        );

        return tree;
    }

    private static int nextVertex() {
        return nextVertex++;
    }

    public static void main(String[] args) {
        double[][] matrix = MatrixLoader.loadFromFile("Files/matrix.txt");
        NeighborJoiner.join(matrix);
    }
}
