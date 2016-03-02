package com.KNS.aligment;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.List;
import java.util.Stack;
import java.util.function.IntBinaryOperator;

/**
 * Created by kodoo on 01.03.2016.
 */
public class ManhattanGraph {

    static int PENALTY = -2;
    static int SCORE = 1;

    private DirectedGraph<Integer, DefaultEdge> graph;
    private int[][] matrix;
    private int width;
    private int height;
    private String protein1;
    private String protein2;

    enum EdgeOrientation {
        VERTICAL, HORIZONTAL, DIAGONAL
    }

    public ManhattanGraph(String protein1, String protein2) {
        this.width = protein1.length() + 1;
        this.height = protein2.length() + 1;
        this.protein1 = protein1;
        this.protein2 = protein2;

        matrix = new int[height][width];
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                graph.addVertex(x + y * width);
            }
        }

        init();

        printValues();
        printNodes();

        Node recordNode = getBestScoreNode();
        System.out.printf("Record score is %d (%d, %d)\n",
                recordNode.value,
                recordNode.x,
                recordNode.y);

        List<DefaultEdge> path = DijkstraShortestPath.findPathBetween(graph, recordNode.x + width * recordNode.y, 0);

        System.out.println("PATH:");
        for (DefaultEdge edge : path) {
            System.out.println(edge);
        }

        align(path);
    }

    public void printValues() {
        System.out.println("VALUES:");
        print(this::getValue);
    }

    public void printNodes() {
        System.out.println("NODES:");
        print((x, y) -> x + width * y);
    }

    void init() {
        for (int x = 0; x < width; x++) {
            setValue(x, 0, 0);
            if (x != width - 1)
                addEdge(x + 1, 0, x, 0);
        }

        for (int y = 0; y < height; y++) {
            setValue(0, y, y * PENALTY);
            if (y != height - 1)
                addEdge(0, y + 1, 0, y);
        }

        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                makeMove(x, y);
            }
        }
    }

    void makeMove(int x, int y) {
        int[] moves = {
                getValue(x - 1, y - 1) + (isMatch(x, y)? SCORE : PENALTY),
                getValue(x - 1, y) + PENALTY,
                getValue(x, y - 1) + PENALTY
        };

        int bestMoveIndex = iMax(moves[0], moves[1], moves[2]);
        setValue(x, y, moves[bestMoveIndex]);

        switch (bestMoveIndex) {
            case 0:
                addEdge(x, y, x - 1, y - 1);
                break;
            case 1:
                addEdge(x, y, x - 1, y);
                break;
            case 2:
                addEdge(x, y, x, y - 1);
                break;
        }
    }

    boolean isMatch(int x, int y) {
        return protein1.charAt(x - 1) == protein2.charAt(y - 1);
    }

    int iMax(int a, int b, int c) {
        if (a >= b && a >= c) { return 0; }
        if (b >= a && b >= c) { return 1; }
        if (c >= a && c >= b) { return 2; }
        return -1;
    }

    public int getValue(int x, int y) {
        return matrix[y][x];
    }

    public void setValue(int x, int y, int value) {
        matrix[y][x] = value;
    }

    void addEdge(int x1, int y1, int x2, int y2) {
        graph.addEdge(
                x1 + y1 * width,
                x2 + y2 * width);
    }

    class Node {
        public int x = -1;
        public int y = -1;
        public int value = Integer.MIN_VALUE;
    }

    private Node getBestScoreNode() {
        Node recordNode = new Node();
        recordNode.x = width - 1;
        for (int y = 0; y < height; y++) {
            int score = getValue(width - 1, y);
            if (score > recordNode.value) {
                recordNode.value = score;
                recordNode.y = y;
            }
        }

        return recordNode;
    }

    void align(List<DefaultEdge> path) {
        Stack<Character> alignment1 = new Stack<>();
        Stack<Character> alignment2 = new Stack<>();

        for (DefaultEdge edge : path) {

            switch (getEdgeOrientation(edge)) {
                case HORIZONTAL:
                    alignment2.push('-');
                    alignment1.push(getProtein1AcidOnEdge(edge));
                    break;
                case VERTICAL:
                    alignment1.push('-');
                    alignment2.push(getProtein2AcidOnEdge(edge));
                    break;
                case DIAGONAL:
                    alignment1.push(getProtein1AcidOnEdge(edge));
                    alignment2.push(getProtein2AcidOnEdge(edge));
            }
        }

        printAlignment(alignment1);
        printAlignment(alignment2, protein2, path.get(0));

    }

    char getProtein1AcidOnEdge(DefaultEdge edge) {
        return protein1.charAt(graph.getEdgeTarget(edge) % width);
    }

    char getProtein2AcidOnEdge(DefaultEdge edge) {
        return protein2.charAt(graph.getEdgeTarget(edge) / width);
    }

    EdgeOrientation getEdgeOrientation(DefaultEdge edge) {
        int edgeDistance = graph.getEdgeSource(edge) - graph.getEdgeTarget(edge);
        if (edgeDistance == 1) {
            return EdgeOrientation.HORIZONTAL;
        } else if (edgeDistance == width) {
            return EdgeOrientation.VERTICAL;
        } else {
            return EdgeOrientation.DIAGONAL;
        }
    }

    void printAlignment(Stack<Character> alignment) {
        System.out.println();
        int alignment1Length = alignment.size();
        for (int i = 0; i < alignment1Length; i++) {
            System.out.print(alignment.pop());
        }
    }

    void printAlignment(Stack<Character> alignment, String protein, DefaultEdge finishEdge) {
        printAlignment(alignment);
        System.out.print(protein.substring(graph.getEdgeTarget(finishEdge) / width, protein.length()));
    }

    public void print(IntBinaryOperator nodeFunc) {
        System.out.print("      ");
        for (int x = 0; x < width - 1; x++) {
            System.out.printf(" %s    ", protein1.charAt(x));
        }
        System.out.println();
        for (int y = 0; y < height; y++) {
            System.out.print("  ");
            for (int x = 0; x < width; x++) {
                System.out.printf("%3d", nodeFunc.applyAsInt(x, y));
                printHorisontalEdge(x, y);
            }
            System.out.println();
            if (y != height - 1)
                System.out.printf("%s ", protein2.charAt(y));
            for (int x = 0; x < width; x++) {
                printVerticalOrdiagonalEdge(x, y);
            }
            System.out.println();
        }
    }

    private void printHorisontalEdge(int x, int y) {
        if (graph.containsEdge(
                x + 1 + y * width,
                x + y * width
        ) && x != width - 1) {
            System.out.print(" ->");
        } else {
            System.out.print("   ");
        }
    }

    private void printVerticalOrdiagonalEdge(int x, int y) {
        if (graph.containsEdge(
                x + (y + 1) * width,
                x + y * width
        ) && x != width - 1) {
            System.out.print(" | ");
        } else {
            System.out.print("   ");
        }
        if (graph.containsEdge(
                x + 1 + (y + 1) * width,
                x + y * width
        ) && x != width - 1) {
            System.out.print("  \\");
        } else {
            System.out.print("   ");
        }
    }

}
