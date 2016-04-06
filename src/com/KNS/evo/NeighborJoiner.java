package com.KNS.evo;

import com.KNS.utils.MatrixLoader;
import com.KNS.utils.MatrixCalculator;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.awt.*;

/**
 * Created by kodoo on 06.04.2016.
 */
public class NeighborJoiner {

    private static int nextVertex = 0;

    public static WeightedGraph<Integer, DefaultEdge> join(int[][] distanceMatrix) {
        final int size = distanceMatrix.length;

        if (size == 2) {
            return createSimpleTree(distanceMatrix[1][1]);
        }

        int[][] neighborJoiningMatrix = MatrixCalculator.calculateNeighborJoinMatrix(distanceMatrix);
        System.out.printf("neighborJoiningMatrix:\n%s\n", MatrixCalculator.matrixToString(neighborJoiningMatrix));

        Point minimalElementPosition = MatrixCalculator.getMinimalElementPosition(neighborJoiningMatrix);
        System.out.printf("minimalElementPosition: %s\n", minimalElementPosition);

        int delta = MatrixCalculator.calculateDelta(
                distanceMatrix,
                minimalElementPosition.x,
                minimalElementPosition.y);
        System.out.printf("Delta: %d\n", delta);

        float limbLengthA = 0.5f * (distanceMatrix[minimalElementPosition.y][minimalElementPosition.x] + delta);
        System.out.printf("limbLengthA = %f\n", limbLengthA);

        float limbLengthB = 0.5f * (distanceMatrix[minimalElementPosition.y][minimalElementPosition.x] - delta);
        System.out.printf("limbLengthB = %f\n", limbLengthB);

        return null;
    }

    private static WeightedGraph<Integer, DefaultEdge> createSimpleTree(float distance) {
        WeightedGraph<Integer, DefaultEdge> tree = new SimpleWeightedGraph<>(DefaultEdge.class);
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
        int[][] matrix = MatrixLoader.loadFromFile("Files/matrix.txt");
        NeighborJoiner.join(matrix);
    }
}
