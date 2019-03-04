package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DirectedGraph {
    protected Map<Integer, List<Integer>> inNodesMap;
    protected Map<Integer, List<Integer>> outNodesMap;

    public DirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap) {
        this.inNodesMap = inNodesMap;
        this.outNodesMap = outNodesMap;
    }

    public DirectedGraph() {
        this.inNodesMap = new LinkedHashMap<>();
        this.outNodesMap = new LinkedHashMap<>();
    }

    public DirectedGraph copy() {
        Map<Integer, List<Integer>> inNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.inNodesMap);
        Map<Integer, List<Integer>> outNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.outNodesMap);
        return new DirectedGraph(inNodesMapCopy, outNodesMapCopy);
    }

    public void loadGraphFromFile(Path filePath) {
        String firstLine = "";
        try {
            firstLine = Files.lines(filePath).findFirst().get();
        } catch (IOException e) {
            System.out.println("Error while reading");
        }
        String[] lineSplit = firstLine.split(" ");
        int numberOfNodes = Integer.valueOf(lineSplit[0]);
        fillMapsWithEmptyLists(numberOfNodes);
        try {
            Files.lines(filePath).skip(1).forEach(this::parseLine);
        } catch (IOException e) {
            System.out.println("Error while reading");
        }
    }

    protected void parseLine(String line) {
        addEdgeToMaps(getInNodeFromLine(line), getOutNodeFromLine(line));
    }

    protected int getInNodeFromLine(String line) {
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[0]);
    }

    protected int getOutNodeFromLine(String line) {
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[1]);
    }

    protected void addEdgeToMaps(int inNode, int outNode) {
        ArrayList updatedList = new ArrayList(this.inNodesMap.get(outNode));
        updatedList.add(inNode);
        this.inNodesMap.put(outNode, updatedList);
        updatedList = new ArrayList(this.outNodesMap.get(inNode));
        updatedList.add(outNode);
        this.outNodesMap.put(inNode, updatedList);
    }

    protected void fillMapsWithEmptyLists(int numberOfNodes) {
        List<Integer> emptyList = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++) {
            this.inNodesMap.put(i, emptyList);
            this.outNodesMap.put(i, emptyList);
        }
    }

    public int getNumberOfVertices() {
        return this.inNodesMap.keySet().size();
    }

    public Iterator getSetOfVerticesIterator() {
        return this.inNodesMap.keySet().iterator();
    }

    public boolean hasEdgeBetween(int inNode, int outNode) {
        return this.outNodesMap.get(inNode).contains(outNode);
    }

    public Edge getEdgeBetween(int inNode, int outNode) throws NullPointerException {
        if (hasEdgeBetween(inNode, outNode)) {
            return new Edge(inNode, outNode);
        } else {
            throw new NullPointerException("graph.Edge does not exist");
        }
    }

    public int getInDegreeOfNode(int node) {
        return this.inNodesMap.get(node).size();
    }

    public int getOutDegreeOfNode(int node) {
        return this.outNodesMap.get(node).size();
    }

    public Iterator getOutboundEdgesOfNodeIterator(int node) { // not O(1) because we have to create edges
        List<Edge> outboundEdgesOfNode = new ArrayList<>();
        for (Integer outNode :
                this.outNodesMap.get(node)) {
            outboundEdgesOfNode.add(new Edge(node, outNode));
        }
        return outboundEdgesOfNode.iterator();
    }

    public Iterator getInboundEdgesOfNodeIterator(int node) {
        List<Edge> inboundEdgesOfNode = new ArrayList<>();
        for (Integer inNode :
                this.inNodesMap.get(node)) {
            inboundEdgesOfNode.add(new Edge(inNode, node));
        }
        return inboundEdgesOfNode.iterator();
    }

    public void addEdge(Edge edge) throws DuplicateEdgeException { // 4 0
        if(this.inNodesMap.get(edge.outNode).contains(edge.inNode) || this.outNodesMap.get(edge.inNode).contains(edge.outNode)) {
            throw new DuplicateEdgeException();
        }

        if(this.inNodesMap.containsKey(edge.outNode)) {
            ArrayList<Integer> updatedInNodesList = new ArrayList<>(this.inNodesMap.get(edge.outNode));
            updatedInNodesList.add(edge.inNode);
            this.inNodesMap.replace(edge.outNode, updatedInNodesList);
        }

        if(this.outNodesMap.containsKey(edge.inNode)) {
            ArrayList<Integer> updatedOutNodesList = new ArrayList<>(this.outNodesMap.get(edge.inNode));
            updatedOutNodesList.add(edge.outNode);
            this.outNodesMap.replace(edge.inNode, updatedOutNodesList);
        }
    }

    public void removeEdge(Edge edge) {
        if(!this.inNodesMap.get(edge.outNode).contains(edge.inNode) || !this.outNodesMap.get(edge.inNode).contains(edge.outNode)) {
            throw new NullPointerException();
        }

        if(this.inNodesMap.containsKey(edge.outNode)) {
            ArrayList<Integer> updatedInNodesList = new ArrayList<>(this.inNodesMap.get(edge.outNode));
            updatedInNodesList.remove((Integer) edge.inNode);
            this.inNodesMap.replace(edge.outNode, updatedInNodesList);
        } else {
            throw new NullPointerException();
        }

        if(this.outNodesMap.containsKey(edge.inNode)) {
            ArrayList<Integer> updatedOutNodesList = new ArrayList<>(this.outNodesMap.get(edge.inNode));
            updatedOutNodesList.remove((Integer) edge.outNode);
            this.outNodesMap.replace(edge.inNode, updatedOutNodesList);
        } else {
            throw new NullPointerException();
        }
    }

    public void addNode(int node) throws NodeAlreadyExistingException {
        if(!this.inNodesMap.containsKey(node)) {
            this.inNodesMap.put(node, new ArrayList<Integer>());
        } else {
            throw new NodeAlreadyExistingException();
        }

        if(!this.outNodesMap.containsKey(node)) {
            this.outNodesMap.put(node, new ArrayList<Integer>());
        } else {
            throw new NodeAlreadyExistingException();
        }
    }

    public void removeNode(int node) {
        ArrayList<Integer> tempArrayList;
        if(this.outNodesMap.containsKey(node)) {
            tempArrayList = new ArrayList(this.outNodesMap.get(node));
            for (Integer inNode :
                    tempArrayList) {
                this.inNodesMap.get(inNode).remove((Integer)node);
            }
            this.outNodesMap.remove(node);
        } else {
            throw new NullPointerException("Node doesn't existing");
        }
        if(this.inNodesMap.containsKey(node)) {
            tempArrayList = new ArrayList(this.inNodesMap.get(node));
            for (Integer outNode :
                    tempArrayList) {
                this.outNodesMap.get(outNode).remove((Integer)node);
            }
            this.inNodesMap.remove(node);
        } else {
            throw new NullPointerException("Node doesn't existing");
        }

    }

    protected Map<Integer, List<Integer>> copy(HashMap<Integer, List<Integer>> originalMap) {
        Map<Integer, List<Integer>> copyMap = new LinkedHashMap<>();
        for(Map.Entry<Integer, List<Integer>> entry : originalMap.entrySet()) {
            copyMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copyMap;
    }


}
