package org.isoContest.common;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ project isoContest
 * @ author pedrogil
 * @ date 11/04/2023
 */


public class Node {

    // ============ ATTRIBUTES ==========
    private final String name;
    private Integer weigh = Integer.MAX_VALUE;
    private final Map<Node, Integer> neighbourNodes = new HashMap<>();
    private List<Node> shortestPath = new LinkedList<>();


    // ============= CONSTRUCTOR ============


    public Node(String name) {
        this.name = name;
    }

    // ============ METHODS ==========
    public void addNeighbour(Node destination, int distance) {
        neighbourNodes.put(destination, distance);
    }


    // ============= GETTERS & SETTERS ============

    public String getName() {
        return name;
    }

    public Integer getWeigh() {
        return weigh;
    }

    public void setWeigh(Integer weigh) {
        this.weigh = weigh;
    }

    public Map<Node, Integer> getNeighbourNodes() {
        return neighbourNodes;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
