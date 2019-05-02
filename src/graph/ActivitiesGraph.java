package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.*;

public class ActivitiesGraph {
    public class Node {
        String name;
        int duration;
        int earliestStartingTime, latestStartingTime, earliestFinishTime, latestFinishTime;

        public Node(String name, int duration){
            this.name = name;
            this.duration = duration;
        }

        public Node(String name){
            this.name = name;
        }

        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(name, node.name);
        }

        @Override
        public int hashCode(){
            return Objects.hash(name);
        }

        @Override
        public String toString(){
            return "Node{" +
                    "name='" + name + '\'' +
                    ", duration=" + duration +
                    ", earliestStarttingTime=" + earliestStartingTime +
                    ", latestStartingTime=" + latestStartingTime +
                    ", earliestFinishTime=" + earliestFinishTime +
                    ", latestFinishTime=" + latestFinishTime +
                    '}';
        }
    }

    private Map<Node, List<Node>> inNodesMap;
    private List<Node> nodesList;

    public ActivitiesGraph(Path filePath) {
        inNodesMap = new HashMap<>();
        nodesList = new ArrayList<>();
        addDummyActivities();
        loadFromFile(filePath);
    }

    private void addDummyActivities(){
        nodesList.add(new Node("START_DUMMY", 0));
        nodesList.add(new Node("END_DUMMY", 0));
    }

    private void loadFromFile(Path filePath){
        String firstLine = "";
        try{
            firstLine = Files.lines(filePath).findFirst().get();
        } catch(IOException e){
            System.err.println("Error while reading");
        }
        String[] lineSplit = firstLine.split(" ");
        int numberOfActivities = Integer.valueOf(lineSplit[0]);
        int numberOfPrecedencies = Integer.valueOf(lineSplit[1]);
        try{
            Files.lines(filePath).skip(1).limit(numberOfActivities).forEach(this::parseCost);
        } catch(IOException e){
            System.out.println("Error while reading activities");
        }
        fillMapWithEmptyLists();
        try{
            Files.lines(filePath).skip(numberOfActivities + 1).forEach(this::parsePrecedence);
        } catch(IOException e){
            System.out.println("Error while reading precedencies");
        }
    }

    private void parsePrecedence(String line){
        String[] lineSplit = line.split(" ");
        inNodesMap.get(nodesList.get(indexInList(lineSplit[1]))).add(nodesList.get(indexInList(lineSplit[0])));
    }

    private int indexInList(String s){
        for(int i = 0; i < nodesList.size(); i++){
            if(nodesList.get(i).equals(new Node(s))){
                return i;
            }
        }
        throw new InvalidParameterException();
    }

    private void fillMapWithEmptyLists(){
        for(Node node : nodesList){
            List<Node> emptyList = new ArrayList<>();
            inNodesMap.put(node, emptyList);
        }
    }

    private void parseCost(String line){
        String[] lineSplit = line.split(" ");
        nodesList.add(new Node(lineSplit[0], Integer.valueOf(lineSplit[1])));
    }

    public Iterator getInboundEdgesOfNodeIterator(Node node) throws NullPointerException{
        if(this.inNodesMap.containsKey(node)){
            List<Node> inboundEdgesOfNode = new ArrayList<>();
            inboundEdgesOfNode.addAll(this.inNodesMap.get(node));
            return inboundEdgesOfNode.iterator();
        } else {
            throw new NullPointerException("Node not found");
        }
    }

    public Iterator getSetOfVerticesIterator(){
        return this.inNodesMap.keySet().iterator();
    }

    public List<Node> topologicalSortList() throws GraphHasCyclesException{
        List<Node> sorted = new LinkedList<>();
        Set<Node> fullyProcessed = new HashSet<>();
        Set<Node> inProcess = new HashSet<>();
        Iterator nodesIterator = getSetOfVerticesIterator();
        Node currentNode;
        boolean ok;
        while(nodesIterator.hasNext()){
            currentNode = (Node) nodesIterator.next();
            if(!fullyProcessed.contains(currentNode)){
                ok = topoSortDFS(currentNode, sorted, fullyProcessed, inProcess);
                if(!ok){
                    throw new GraphHasCyclesException();
                }
            }
        }
        return sorted;
    }

    private boolean topoSortDFS(Node currentNode, List<Node> sorted, Set<Node> fullyProcessed, Set<Node> inProcess){
        inProcess.add(currentNode);
        Iterator inNodesOfCurrentNode = getInboundEdgesOfNodeIterator(currentNode);
        Node currentInNode;
        boolean ok;
        while(inNodesOfCurrentNode.hasNext()){
            currentInNode = (Node) inNodesOfCurrentNode.next();
            if(inProcess.contains(currentInNode)){
                return false;
            } else if(!fullyProcessed.contains(currentInNode)) {
                ok = topoSortDFS(currentInNode, sorted, fullyProcessed, inProcess);
                if(!ok) return false;
            }
        }
        inProcess.remove(currentNode);
        sorted.add(currentNode);
        fullyProcessed.add(currentNode);
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Activities graph\n");
        sb.append("In nodes map\n");
        for(Node node :
                inNodesMap.keySet()){
            sb.append(node.name).append(": ");
            ArrayList<Node> outNodes = (ArrayList) inNodesMap.get(node);
            for(Node outNode :
                    outNodes){
                sb.append(outNode.name).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        sb.append("Nodes:\n");
        for(Node node :
                nodesList){
            sb.append(node.toString()).append("\n");
        }
        return sb.toString();
    }
}
