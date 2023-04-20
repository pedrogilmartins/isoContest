package org.isoContest;


/**
 * Cheaters
 * You organize the Master Dev France in the city of Paris and you want to hide the winner's check in a building so that
 * no cheater can get to that building before the winner. More precisely, the city of Paris is composed of N buildings
 * and M bidirectional roads, it is possible to move between any pair of buildings by using the roads. The winner is
 * currently located in building number 1, while the T cheaters are located at buildings u_1, u_2, ..., u_T. You must
 * return the sorted list of all buildings where the winner can arrive strictly before the cheaters.
 * <p>
 * Data
 * Input
 * Line 1: Three integers N (1 <= N <= 10^4) the number of buildings, M (1 <= M <= 2 * 10^4) the number of roads,
 * T (1 <= T <= N) the number of cheaters. Line 2: T distinct integers between 2 and N : The initial positions of
 * the cheaters. The following M lines: two integers u and v, indicating that there is a route between u and v
 * (1 <= u < v <= N).
 * <p>
 * Output
 * If there are K buildings where the winner can arrive before the cheaters, you must return K integers sorted on one
 * line: the list of buildings in question.
 * <p>
 * Example
 * <p>
 * For the input :
 * 7 6 2
 * 5 6
 * 1 2
 * 1 3
 * 2 4
 * 4 5
 * 3 6
 * 3 7
 * <p>
 * The answer is:
 * 1 2
 */

import org.isoContest.common.DijkstraAlgorithm;
import org.isoContest.common.Graph;
import org.isoContest.common.Node;


import java.util.*;
import java.util.stream.Collectors;

public class Mdf3_Round2 {

    private static final Graph graph = new Graph();
    private static final Map<Integer, Node> nodesMap = new HashMap<>();
    private static final List<Node> cheaterPositions = new ArrayList<>();


    public static void main(String[] args) {
        String line;
        Scanner sc = new Scanner(System.in);

        line = sc.nextLine();
        handleFirstLine(line);

        line = sc.nextLine();
        handleCheaters(line);

        while (sc.hasNextLine()) {
            line = sc.nextLine();

            String[] edge = line.split(" ");
            int edgeStart = Integer.parseInt(edge[0]);
            int edgeEnd = Integer.parseInt(edge[1]);

            Node start = nodesMap.get(edgeStart);
            Node end = nodesMap.get(edgeEnd);

            start.addNeighbour(end, 1);
            end.addNeighbour(start, 1);
        }

        DijkstraAlgorithm.calculateShortestPathFromSource(graph, nodesMap.get(1));
        Map<Node, Integer> fromSource = graph.getWeighsMap();

        Map<Node, Map<Node, Integer>> fromNode = new HashMap<>();
        for (Node node : cheaterPositions){
            DijkstraAlgorithm.calculateShortestPathFromSource(graph.initialize(), node);
            fromNode.put(node, graph.getWeighsMap());
        }

        List<Node> result = new ArrayList<>();
        for (Node node : cheaterPositions){
            List<Node> closerToSource = getCloserToSource(fromSource, fromNode.get(node));
            if (result.isEmpty()){
                result.addAll(closerToSource);
            } else {
                result = result.stream()
                        .filter(closerToSource::contains)
                        .collect(Collectors.toList());
            }
        }

        System.out.println(result.stream()
                .map(Node::getName)
                .collect(Collectors.joining(" ")));
    }

    private static List<Node> getCloserToSource(Map<Node, Integer> fromSource, Map<Node, Integer> fromCheater) {
        List<Node> closerToSource = new ArrayList<>();
        for (Node node : nodesMap.values()){
            if (fromSource.get(node) < fromCheater.get(node)){
                closerToSource.add(node);
            }
        }
        return closerToSource;
    }


    private static void handleFirstLine(String line) {
        String[] values = line.split(" ");
        int nbBuildings = Integer.parseInt(values[0]);

        createNodes(nbBuildings);
    }


    private static void handleCheaters(String line) {
        List<Integer> positions = Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .toList();

        for (Integer position : positions) {
            cheaterPositions.add(nodesMap.get(position));
        }
    }


    private static void createNodes(int nbBuildings) {
        for (int i = 0; i < nbBuildings; i++) {

            Node node = new Node(String.valueOf(i + 1));
            nodesMap.put(i + 1, node);
            graph.addNode(node);
        }
    }

}
