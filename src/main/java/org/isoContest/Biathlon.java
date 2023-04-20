package org.isoContest;

/**
 * Biathlon in rice
 * To finish the world tour, let's go to Bangkok for the biathlon event, which will take place in the middle of the
 * Thai rice terraces!
 * In these rice fields that take the form of stairs to follow the shape of the hills, the ski course is done from a
 * high level and the shooting targets will be placed below. To make sure that the shooting angles will not be too
 * extreme, you want to calculate the vertical distance between the firing point and the target. To help you in this
 * task, you have at your disposal a map of the rice fields on the competition site.
 * This map is given to you in the form of a 2D grid, in which a . represents a rice field, a # is a change of floor,
 * A represents the firing point and B marks the position of the targets.
 * <p>
 * Data
 * Input
 * Line 1: Two integers W and H between 1 and 50, representing the width and height of your plan.
 * H next lines : A string of characters of size W representing a line in the plan.
 * Output
 * The altitude difference, also defined as the minimum number of floor changes to go from point A to point B.
 * Example
 * For the input :
 * 15 10
 * ...#...#..##...
 * .A.#..##.##...#
 * ..##.##..#...##
 * .##..#...#..##.
 * .#...#...#..#..
 * .#...##..#..#.B
 * .##...##.##.##.
 * ..#....#..#..##
 * ..##...#..##..#
 * ...#...#...#...
 * The expected result is :
 * 4
 */

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Biathlon {

    private static int NB_ROWS;
    private static int NB_COLUMNS;

    static int[] rowMoves = new int[]{1, 0, -1, 0};
    static int[] colMoves = new int[]{0, 1, 0,  -1};

    private static Node stNode;
    private static Node endNode;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        readDimensions(sc);

        Node[][] matrix = new Node[NB_ROWS][NB_COLUMNS];
        boolean[][] visited = new boolean[NB_ROWS][NB_COLUMNS];

        int rowNumber = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            matrix[rowNumber] = createLineNodes(line, rowNumber);
            rowNumber++;
        }

        fillWithNeighbours(matrix);

        calculateShortestDistance(matrix, stNode, endNode, visited);

        //printArray(matrix);
        //printNbNeighbours(matrix);
        //printDistances(matrix);

        System.out.println(endNode.getDistanceToSource());
    }

    private static void calculateShortestDistance(Node[][] matrix, Node stNode, Node endNode, boolean[][] visited) {

        stNode.setDistanceToSource(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(stNode);

        while (unsettledNodes.size() != 0) {
            Node current = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(current);

            for (Node neighbour : current.getNeighbours()) {

                Integer edgeWeight = getWeight(current, neighbour);

                if (!settledNodes.contains(neighbour)) {
                    calculateMinimumDistance(neighbour, edgeWeight, current);
                    unsettledNodes.add(neighbour);
                }
            }
            settledNodes.add(current);
        }
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistanceToSource();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistanceToSource()) {
            evaluationNode.setDistanceToSource(sourceDistance + edgeWeigh);
        }
    }

    private static Integer getWeight(Node current, Node neighbour) {
        if (current.getValue().equals(".") && neighbour.getValue().equals("#"))
            return 1;

        if (current.getValue().equals(".") && neighbour.getValue().equals("."))
            return 0;

        if (current.getValue().equals("#") && neighbour.getValue().equals("#"))
            return 1;

        if (current.getValue().equals("#") && neighbour.getValue().equals("."))
            return 0;

        return null;
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistanceToSource();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void fillWithNeighbours(Node[][] matrix) {
        for (Node[] nodes : matrix) {
            for (int col = 0; col < matrix[0].length; col++) {
                findNeighbours(nodes[col], matrix);
            }
        }
    }

    private static void findNeighbours(Node current, Node[][] matrix) {
        for (int i = 0; i < rowMoves.length; i++) {
            Coordinates coordinates = current.getCoordinates();
            int possibleRow = coordinates.getRow() + rowMoves[i];
            int possibleCol = coordinates.getColumn() + colMoves[i];
            if (isSafe(possibleRow, possibleCol)) {
                current.getNeighbours().add(matrix[possibleRow][possibleCol]);
            }
        }
    }

    private static Node[] createLineNodes(String line, int rowNumber) {
        Node[] lineMatrix = new Node[NB_COLUMNS];
        for (int colIdx = 0; colIdx < line.length(); colIdx++) {
            char c = line.charAt(colIdx);

            Node current = new Node();
            current.value = String.valueOf(c);
            current.coordinates = new Coordinates(rowNumber, colIdx);

            switch (c) {
                case 'A' -> {
                    current.weight = 0;
                    current.value = ".";
                    current.distanceToSource = 0;
                    stNode = current;
                }
                case 'B' -> {
                    current.weight = 0;
                    current.value = ".";
                    endNode = current;
                }
                case '.' -> {
                    current.weight = 0;
                }
                default -> {
                    current.weight = 1;
                }
            }

            lineMatrix[colIdx] = current;
        }
        return lineMatrix;
    }

    private static void readDimensions(Scanner sc) {
        String[] size = sc.nextLine().split(" ");
        NB_ROWS = Integer.parseInt(size[1]);
        NB_COLUMNS = Integer.parseInt(size[0]);
    }

    static boolean isSafe(int row, int col) {
        return row >= 0 && col >= 0 && row < NB_ROWS && col < NB_COLUMNS;
    }


    private static void printArray(Node[][] matrix) {
        for (Node[] line : matrix) {
            for (Node column : line) {
                System.out.print(column.getValue() + " ");
            }
            System.out.println();
        }
    }

    private static void printNbNeighbours(Node[][] matrix) {
        for (Node[] line : matrix) {
            for (Node column : line) {
                System.out.print(column.getNeighbours().size() + " ");
            }
            System.out.println();
        }
    }

    private static void printArray(int[][][] matrix) {
        for (int[][] line : matrix) {
            for (int[] column : line) {
                System.out.print(column[0] + " ");
            }
            System.out.println();
        }
    }

    private static void printDistances(Node[][] matrix) {
        for (Node[] line : matrix) {
            for (Node column : line) {
                System.out.printf("%1s", column.getDistanceToSource() + " ");
            }
            System.out.println();
        }
    }

    private static void printDistances(int[][][] matrix) {
        for (int[][] line : matrix) {
            for (int[] column : line) {
                System.out.printf("%11s", column[1] + " ");
            }
            System.out.println();
        }
    }

}


@Getter
@Setter
class Node {
    String value;
    Coordinates coordinates;
    int weight;
    int distanceToSource;
    List<Node> neighbours = new ArrayList<>();

    public Node() {
        this.distanceToSource = Integer.MAX_VALUE;
    }
}


@Getter
@Setter
class Coordinates {
    int row;
    int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
