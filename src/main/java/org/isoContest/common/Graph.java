package org.isoContest.common;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ project isoContest
 * @ author pedrogil
 * @ date 11/04/2023
 */


public class Graph {

    // ============ ATTRIBUTES ==========
    private final Set<Node> nodes = new HashSet<>();


    // ============ METHODS ==========
    public void addNode(Node node) {
        nodes.add(node);
    }

    public Map<Node, Integer> getWeighsMap() {
        Map<Node, Integer> weighs = new HashMap<>();
        List<Node> sortedList = nodes.stream()
                .sorted(Comparator.comparing(Node::getName)) //comparator - how you want to sort it
                .toList();

        for (Node node : sortedList) {
            weighs.put(node, node.getWeigh());
        }
        return weighs;
    }

    public Graph initialize() {
        for (Node node : nodes){
            node.setWeigh(Integer.MAX_VALUE);
        }
        return this;
    }

    public void print() {
        System.out.println();

        List<Node> sortedList = nodes.stream()
                .sorted(Comparator.comparing(Node::getName)) //comparator - how you want to sort it
                .toList();

        for (Node node : sortedList) {
            System.out.println(node.getName() + " -> " + node.getNeighbourNodes()
                    .keySet()
                    .stream()
                    .map(Node::getName)
                    .collect(Collectors.joining(", ")));
        }
    }

    public void printDistances() {
        System.out.println();

        List<Node> sortedList = nodes.stream()
                .sorted(Comparator.comparing(Node::getName)) //comparator - how you want to sort it
                .toList();

        for (Node node : sortedList) {
            System.out.println(node.getName() + ": " + node.getWeigh());
        }
    }

    public void printShortestPath() {
        System.out.println();

        List<Node> sortedList = nodes.stream()
                .sorted(Comparator.comparing(Node::getName)) //comparator - how you want to sort it
                .toList();

        for (Node node : sortedList) {
            System.out.println(node.getName() + ": " + node.getShortestPath()
                    .stream()
                    .map(Node::getName)
                    .collect(Collectors.joining(" -> ")));
        }
    }


    // ============= GETTERS & SETTERS ===========


    public Set<Node> getNodes() {
        return nodes;
    }
}
