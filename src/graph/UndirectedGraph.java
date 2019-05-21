package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class UndirectedGraph {
    private Map<Integer, List<Integer>> nodesMap;
    public int[][] adjacencyMatrix;
    public int nodesNumber;

    public UndirectedGraph(Map<Integer, List<Integer>> nodesMap, int[][] adjacencyMatrix){
        this.nodesMap = nodesMap;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public UndirectedGraph(){
        this.nodesMap = new LinkedHashMap<>();
        this.adjacencyMatrix = new int[50][50];
    }

    public UndirectedGraph(Path filePath){
        this.nodesMap = new LinkedHashMap<>();
        loadGraphFromFile(filePath);
    }

    private void loadGraphFromFile(Path filePath){
        String firstLine = "";
        try{
            firstLine = Files.lines(filePath).findFirst().get();
        } catch(IOException e){
            System.err.println("Error while reading");
        }
        String[] lineSplit = firstLine.split(" ");
        int numberOfNodes = Integer.valueOf(lineSplit[0]);
        fillMapWithEmptyList(numberOfNodes);
        try{
            Files.lines(filePath).skip(1).forEach(this::parseLine);
        } catch(IOException e){
            System.out.println("Error while reading");
        }
    }

    public void loadWeightedFromFile(Path filePath){
        String firstLine = "";
        try{
            firstLine = Files.lines(filePath).findFirst().get();
        } catch(IOException e){
            System.err.println("Error while reading");
        }
        String[] lineSplit = firstLine.split(" ");
        int numberOfNodes = Integer.valueOf(lineSplit[0]);
        nodesNumber = numberOfNodes;
        fillAdjacencyMatrix(numberOfNodes);
        try{
            Files.lines(filePath).skip(1).forEach(this::parseLineToAdjacencyMatrix);
        } catch(IOException e){
            System.out.println("Error while reading");
        }
    }

    private void parseLineToAdjacencyMatrix(String line){
        addEdgeToAdjacencyMatrix(getInNodeFromLine(line), getOutNodeFromLine(line), getCostFromLine(line));
    }

    private void addEdgeToAdjacencyMatrix(int inNodeFromLine, int outNodeFromLine, int costFromLine){
        adjacencyMatrix[inNodeFromLine][outNodeFromLine] = costFromLine;
        adjacencyMatrix[outNodeFromLine][inNodeFromLine] = costFromLine;
    }

    private int getCostFromLine(String line){
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[2]);
    }

    private void fillAdjacencyMatrix(int numberOfNodes){
        for(int i = 0; i < numberOfNodes; i++){
            for(int j = 0; j < numberOfNodes; j++){
                adjacencyMatrix[i][j] = 0;
            }
        }
    }

    private void parseLine(String string){
        addEdge(new Edge(getInNodeFromLine(string), getOutNodeFromLine(string)));
    }

    private void fillMapWithEmptyList(int numberOfNodes){
        for(int i = 0; i < numberOfNodes; i++){
            List<Integer> emptyList = new ArrayList<>();
            this.nodesMap.put(i, emptyList);
        }
    }

    private void addEdge(Edge edge){
        addNode(edge.inNode);
        addNode(edge.outNode);
        if(!nodesMap.get(edge.inNode).contains(edge.outNode)){
            nodesMap.get(edge.inNode).add(edge.outNode);
        }
        if(!nodesMap.get(edge.outNode).contains(edge.inNode)){
            nodesMap.get(edge.outNode).add(edge.inNode);
        }
    }

    private void addNode(int node){
        if(!nodesMap.containsKey(node)){
            nodesMap.put(node, new ArrayList<>());
        }
    }

    public List<UndirectedGraph> subgraphs(){
        List<UndirectedGraph> result = new ArrayList<>();
        ArrayList<Boolean> visited = new ArrayList<>();
        for(int i = 0; i < nodesMap.size(); i++){
            visited.add(false);
        }
        for(int i = 0; i < visited.size(); i++){
            if(!visited.get(i)){
                result.add(breadthFirstSearch(i, visited));
            }
        }
        return result;
    }

    public int numberOfSubgraphs(){
        return subgraphs().size();
    }

    private UndirectedGraph breadthFirstSearch(int startNode, ArrayList<Boolean> visited){
        UndirectedGraph result = new UndirectedGraph();
        LinkedList<Integer> queue = new LinkedList<>();
        return getUndirectedGraph(startNode, visited, result, queue);
    }

    public boolean hasEdge(Edge edge){
        return nodesMap.get(edge.inNode).contains(edge.outNode) || nodesMap.get(edge.outNode).contains(edge.inNode);
    }

    private UndirectedGraph getUndirectedGraph(int startNode, ArrayList<Boolean> visited, UndirectedGraph result, LinkedList<Integer> queue){
        int currentNode;
        queue.push(startNode);
        while(!queue.isEmpty()){
            currentNode = queue.poll();
            result.addNode(currentNode);
            visited.set(currentNode, true);
            for(int nextNode : nodesMap.get(currentNode)){
                if(!visited.get(nextNode)){
                    visited.set(nextNode, true);
                    queue.add(nextNode);
                    result.addEdge(new Edge(currentNode, nextNode));
                }
            }
        }
        for(int inNode : result.nodesMap.keySet()){ // todo this adds the edges not present from the bfs
            for(int outNode : result.nodesMap.keySet()){
                if(inNode != outNode){
                    if(hasEdge(new Edge(inNode, outNode)) && !result.hasEdge(new Edge(inNode, outNode))){
                        result.addEdge(new Edge(inNode, outNode));
                    }
                }
            }
        }
        return result;
    }

    public UndirectedGraph breadthFirstSearch(int startNode){
        UndirectedGraph result = new UndirectedGraph();
        LinkedList<Integer> queue = new LinkedList<>();
        ArrayList<Boolean> visited = new ArrayList<>();
        for(int i = 0; i < nodesMap.size(); i++){
            visited.add(false);
        }
        return getUndirectedGraph(startNode, visited, result, queue);
    }

    protected int getInNodeFromLine(String line){
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[0]);
    }

    protected int getOutNodeFromLine(String line){
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[1]);
    }

    private int minKey(int key[], Boolean mstSet[]){
        int min = Integer.MAX_VALUE, minimumIndex = -1;
        for (int node = 0; node < nodesNumber; node++){
            if(!mstSet[node] && key[node] < min){
                min = key[node];
                minimumIndex = node;
            }
        }
        return minimumIndex;
    }

    public int[] getMinimumSpanningTree(){
        int[] builtMST = new int[nodesNumber];
        int[] key = new int [nodesNumber];
        Boolean[] includedSet = new Boolean[nodesNumber];
        for (int i = 0; i < nodesNumber; i++){
            key[i] = Integer.MAX_VALUE;
            includedSet[i] = false;
        }
        key[0] = 0;
        builtMST[0] = -1;
        for (int count = 0; count < nodesNumber - 1; count++){
            int currentNode = minKey(key, includedSet);
            System.out.println(currentNode);
            includedSet[currentNode] = true;
            for (int temporaryNode = 0; temporaryNode < nodesNumber; temporaryNode++)
                if (adjacencyMatrix[currentNode][temporaryNode] != 0 && !includedSet[temporaryNode] && adjacencyMatrix[currentNode][temporaryNode] < key[temporaryNode]){
                    builtMST[temporaryNode] = currentNode;
                    key[temporaryNode] = adjacencyMatrix[currentNode][temporaryNode];
                }
        }
        return builtMST;
    }

    public List<Integer> hamiltonianCycle(){
        List<Integer> solution = new ArrayList<>();
        traverse(0, solution);
        solution.add(0);
        return solution;
    }

    private void traverse(int node, List<Integer> solution){
        solution.add(node);
        for(int child : getChildren(node)){
            if(!solution.contains(child)){
                traverse(child, solution);
            }
        }
    }

    private List<Integer> getChildren(int node){
        List<Integer> result = new ArrayList<>();
        int[][] adjacencyOfMST = adjacencyMTS();
        for(int i = 0; i < nodesNumber; i++){
            if(adjacencyOfMST[node][i] != 0) result.add(i);
        }
        return result;
    }

    public int[][] adjacencyMTS(){
        int[][] result = new int[nodesNumber][nodesNumber];
        for(int i = 0; i < nodesNumber; i++){
            for(int j = 0; j < nodesNumber; j++){
                result[i][j] = 0;
            }
        }
        for(int i = 1; i < nodesNumber; i++){
            result[getMinimumSpanningTree()[i]][i] = adjacencyMatrix[i][getMinimumSpanningTree()[i]];
            result[i][getMinimumSpanningTree()[i]] = adjacencyMatrix[i][getMinimumSpanningTree()[i]];
           // System.out.println(getMinimumSpanningTree()[i] + " - " + i + "\t" + adjacencyMatrix[i][getMinimumSpanningTree()[i]]);
        }
        return result;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Undirected graph\n");
        sb.append("Nodes map\n");
        for(Integer node :
                nodesMap.keySet()){
            sb.append(node).append(": ");
            ArrayList<Integer> outNodes = (ArrayList) nodesMap.get(node);
            for(Integer outNode :
                    outNodes){
                sb.append(outNode).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        return sb.toString();
    }
}