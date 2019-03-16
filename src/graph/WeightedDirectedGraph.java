package graph;

import java.util.*;

public class WeightedDirectedGraph extends DirectedGraph {
    private Map<Edge, Integer> weightsMap;

    public WeightedDirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap, Map<Edge, Integer> weightsMap) {
        super(inNodesMap, outNodesMap);
        this.weightsMap = weightsMap;
    }

    /**
     * Return a deep copy of current weighted directed graph.
     */
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

    @Override
    protected void parseLine(String line) {
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

    /**
     * Get weight of existing edge.
     */
    public int getWeight(Edge edge) {
        if(this.weightsMap.containsKey(edge)) {
            return this.weightsMap.get(edge);
        } else {
            throw new NullPointerException("graph.Edge not found");
        }
    }

    /**
     * Update weight of existing edge.
     */
    public void updateWeight(Edge edge, int newWeight) {
        if(this.weightsMap.containsKey(edge)) {
            this.weightsMap.replace(edge, newWeight);
        } else {
            throw new NullPointerException("graph.Edge not found");
        }
    }

    @Override
    public int getNumberOfEdges() {
        return this.weightsMap.keySet().size();
    }

    private Map<Edge, Integer> copy(Map<Edge, Integer> originalMap) {
        Map<Edge, Integer> copyMap = new LinkedHashMap<>();
        for(Map.Entry<Edge, Integer> entry : originalMap.entrySet()) {
            copyMap.put(entry.getKey().copy(), entry.getValue());
        }
        return copyMap;
    }

    @Override
    public void removeEdge(Edge edge) {
        super.removeEdge(edge);
        if(this.weightsMap.containsKey(edge)) {
            this.weightsMap.remove(edge);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void removeNode(int node){
        super.removeNode(node);
        weightsMap.entrySet().removeIf(e -> e.getKey().containsNode(node));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("Weights map\n");
        for (Edge edge :
                weightsMap.keySet()) {
            sb.append(edge.inNode).append(" -> ").append(edge.outNode).append(": ").append(weightsMap.get(edge));
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addEdge(Edge edge, int weight) throws DuplicateEdgeException{
        if(this.inNodesMap.get(edge.outNode).contains(edge.inNode) || this.outNodesMap.get(edge.inNode).contains(edge.outNode)){
            throw new DuplicateEdgeException();
        }

        if(this.inNodesMap.containsKey(edge.outNode)){
            ArrayList<Integer> updatedInNodesList = new ArrayList<>(this.inNodesMap.get(edge.outNode));
            updatedInNodesList.add(edge.inNode);
            this.inNodesMap.replace(edge.outNode, updatedInNodesList);
        }

        if(this.outNodesMap.containsKey(edge.inNode)){
            ArrayList<Integer> updatedOutNodesList = new ArrayList<>(this.outNodesMap.get(edge.inNode));
            updatedOutNodesList.add(edge.outNode);
            this.outNodesMap.replace(edge.inNode, updatedOutNodesList);
        }

        this.weightsMap.put(edge, weight);
    }
}
