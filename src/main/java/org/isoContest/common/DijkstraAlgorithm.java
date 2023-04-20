package org.isoContest.common;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * @ project isoContest
 * @ author pedrogil
 * @ date 11/04/2023
 */
public class DijkstraAlgorithm {


    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setWeigh(0);

        Set<Node> settled = new HashSet<>();
        Set<Node> unsettled = new HashSet<>();

        unsettled.add(source);

        while (unsettled.size() != 0) {
            Node current = getLowestDistanceNode(unsettled);
            unsettled.remove(current);
            for (Map.Entry<Node, Integer> adjacencyPair : current.getNeighbourNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                if (!settled.contains(adjacentNode)) {
                    Integer edgeWeight = adjacencyPair.getValue();
                    calculateMinimumDistance(adjacentNode, edgeWeight, current);
                    unsettled.add(adjacentNode);
                }
            }
            settled.add(current);
        }
        return graph;
    }


    private static Node getLowestDistanceNode(Set<Node> unsettled) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node node : unsettled) {
            int nodeDistance = node.getWeigh();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }


    private static void calculateMinimumDistance(Node neighbour, Integer edgeWeigh, Node current) {

        Integer sourceWeigh = current.getWeigh();

        if (sourceWeigh + edgeWeigh < neighbour.getWeigh()) {
            neighbour.setWeigh(sourceWeigh + edgeWeigh);

            LinkedList<Node> shortestPath = new LinkedList<>(current.getShortestPath());
            shortestPath.add(current);
            neighbour.setShortestPath(shortestPath);
        }
    }


}
