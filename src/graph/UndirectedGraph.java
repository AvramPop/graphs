package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class UndirectedGraph {
    private Map<Integer, List<Integer>> nodesMap;

    public UndirectedGraph(Map<Integer, List<Integer>> nodesMap){
        this.nodesMap = nodesMap;
    }

    public UndirectedGraph(){
        this.nodesMap = new LinkedHashMap<>();
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