package graph;

import java.util.*;

public class WeightedDirectedGraph extends DirectedGraph {
    private Map<Edge, Integer> weightsMap;

    public WeightedDirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap, Map<Edge, Integer> weightsMap) {
        super(inNodesMap, outNodesMap);
        this.weightsMap = weightsMap;
    }

    public WeightedDirectedGraph copy() {
        Map<Integer, List<Integer>> inNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.inNodesMap);
        Map<Integer, List<Integer>> outNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.outNodesMap);
        Map<Edge, Integer> weightsMapCopy = copy(this.weightsMap);
        return new WeightedDirectedGraph(inNodesMapCopy, outNodesMapCopy, weightsMapCopy);
    }

    public WeightedDirectedGraph() {
        super();
        this.weightsMap = new LinkedHashMap<>();
    }

    private void parseLine(String line) {
        addWightedEdgeForLine(getInNodeFromLine(line), getOutNodeFromLine(line), getWeightFromLine(line));
    }

    private int getWeightFromLine(String line) {
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[2]);
    }

    private void addWightedEdgeForLine(int inNodeFromLine, int outNodeFromLine, int weightFromFile) {
        addEdgeToMaps(inNodeFromLine, outNodeFromLine);
        addWeightFromLine(inNodeFromLine, outNodeFromLine, weightFromFile);
    }

    private void addWeightFromLine(int inNodeFromFile, int outNodeFromFile, int weightFromFile) {
        this.weightsMap.put(new Edge(inNodeFromFile, outNodeFromFile), weightFromFile);
    }

    public int getWeight(Edge edge) {
        if(this.weightsMap.containsKey(edge)) {
            return this.weightsMap.get(edge);
        } else {
            throw new NullPointerException("graph.Edge not found");
        }
    }

    public void updateWeight(Edge edge, int newWeight) {
        if(this.weightsMap.containsKey(edge)) {
            this.weightsMap.replace(edge, newWeight);
        } else {
            throw new NullPointerException("graph.Edge not found");
        }
    }

    private Map<Edge, Integer> copy(Map<Edge, Integer> originalMap) {
        Map<Edge, Integer> copyMap = new LinkedHashMap<>();
        for(Map.Entry<Edge, Integer> entry : originalMap.entrySet()) {
            copyMap.put(entry.getKey().copy(), entry.getValue());
        }
        return copyMap;
    }
}
