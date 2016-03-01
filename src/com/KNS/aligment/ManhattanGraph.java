package com.KNS.aligment;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by kodoo on 01.03.2016.
 */
public class ManhattanGraph {

    DirectedGraph<Integer, DefaultEdge> graph;
    int[][] matrix;
    int width;
    int height;

    public ManhattanGraph(int width, int height) {
        this.width = width;
        this.height = height;

        matrix = new int[height][width];
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                graph.addVertex(x + y * width);
            }
        }
    }

    public int getValue(int x, int y) {
        return matrix[y][x];
    }

    public void setValue(int x, int y, int value) {
        matrix[y][x] = value;
    }

    void addAdge(int x1, int y1, int x2, int y2) {
        graph.addEdge(
                x1 + y1 * width,
                x2 + y2 * width);
    }

    public static void main(String[] args) {
        ManhattanGraph gr = new ManhattanGraph(10, 10);
    }


}
