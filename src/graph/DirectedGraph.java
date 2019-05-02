package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Class modeling an oriented graph.
 */
public class DirectedGraph {
    protected Map<Integer, List<Integer>> inNodesMap;
    protected Map<Integer, List<Integer>> outNodesMap;

    /**
     * Create directed graph from existing nodesMap and outNodesMap.
     */
    public DirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap){
        this.inNodesMap = inNodesMap;
        this.outNodesMap = outNodesMap;
    }

    /**
     * Create an empty directed graph.
     */
    public DirectedGraph(){
        this.inNodesMap = new LinkedHashMap<>();
        this.outNodesMap = new LinkedHashMap<>();
    }

    /**
     * Return a deep copy of current directed graph.
     */
    public DirectedGraph copy(){
        Map<Integer, List<Integer>> inNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.inNodesMap);
        Map<Integer, List<Integer>> outNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.outNodesMap);
        return new DirectedGraph(inNodesMapCopy, outNodesMapCopy);
    }

    /**
     * Load directed graph from file formatted accordingly.
     */
    public void loadGraphFromFile(Path filePath){
        String firstLine = "";
        try{
            firstLine = Files.lines(filePath).findFirst().get();
        } catch(IOException e){
            System.err.println("Error while reading");
        }
        String[] lineSplit = firstLine.split(" ");
        int numberOfNodes = Integer.valueOf(lineSplit[0]);
        fillMapsWithEmptyLists(numberOfNodes);
        try{
            Files.lines(filePath).skip(1).forEach(this::parseLine);
        } catch(IOException e){
            System.out.println("Error while reading");
        }
    }

    protected void parseLine(String line){
        addEdgeToMaps(getInNodeFromLine(line), getOutNodeFromLine(line));
    }

    protected int getInNodeFromLine(String line){
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[0]);
    }

    protected int getOutNodeFromLine(String line){
        String[] lineSplit = line.split(" ");
        return Integer.valueOf(lineSplit[1]);
    }

    protected void addEdgeToMaps(int inNode, int outNode){
        ArrayList updatedList = new ArrayList(this.inNodesMap.get(outNode));
        updatedList.add(inNode);
        this.inNodesMap.put(outNode, updatedList);
        updatedList = new ArrayList(this.outNodesMap.get(inNode));
        updatedList.add(outNode);
        this.outNodesMap.put(inNode, updatedList);
    }

    protected void fillMapsWithEmptyLists(int numberOfNodes){
        for(int i = 0; i < numberOfNodes; i++){
            List<Integer> emptyList = new ArrayList<>();
            this.inNodesMap.put(i, emptyList);
            List<Integer> emptyList2 = new ArrayList<>();
            this.outNodesMap.put(i, emptyList2);
        }
    }

    /**
     * Get number of vertices of graph.
     */
    public int getNumberOfVertices(){
        return this.inNodesMap.keySet().size();
    }

    /**
     * Get iterator on the set of vertices.
     */
    public Iterator getSetOfVerticesIterator(){
        return this.inNodesMap.keySet().iterator();
    }

    /**
     * Check whether there is an edge between given nodes.
     */
    public boolean hasEdgeBetween(int inNode, int outNode){
        return this.outNodesMap.get(inNode).contains(outNode);
    }

    /**
     * Get edge between given nodes.
     */
    public Edge getEdgeBetween(int inNode, int outNode) throws NullPointerException{
        if(hasEdgeBetween(inNode, outNode)){
            return new Edge(inNode, outNode);
        } else{
            throw new NullPointerException("graph.Edge does not exist");
        }
    }

    public int getInDegreeOfNode(int node) throws NullPointerException{
        if(this.inNodesMap.containsKey(node)){
            return this.inNodesMap.get(node).size();
        } else {
            throw new NullPointerException("don't have that vertex");
        }
    }

    public int getOutDegreeOfNode(int node) throws NullPointerException{
        if(this.outNodesMap.containsKey(node)){
            return this.outNodesMap.get(node).size();
        } else {
            throw new NullPointerException("don't have that vertex");
        }
    }

    /**
     * Get Iterator over outbound edges of node.
     */
    public Iterator getOutboundEdgesOfNodeIterator(int node) throws NullPointerException{
        if(this.outNodesMap.containsKey(node)){
            List<Edge> outboundEdgesOfNode = new ArrayList<>();
            for(Integer outNode :
                    this.outNodesMap.get(node)){
                outboundEdgesOfNode.add(new Edge(node, outNode));
            }
            return outboundEdgesOfNode.iterator();
        } else {
            throw new NullPointerException("Node not found");
        }
    }

    /**
     * Get Iterator over inbound edges of node.
     */
    public Iterator getInboundEdgesOfNodeIterator(int node) throws NullPointerException{
        if(this.inNodesMap.containsKey(node)){
            List<Edge> inboundEdgesOfNode = new ArrayList<>();
            for(Integer inNode :
                    this.inNodesMap.get(node)){
                inboundEdgesOfNode.add(new Edge(inNode, node));
            }
            return inboundEdgesOfNode.iterator();
        } else {
            throw new NullPointerException("Node not found");
        }
    }

    /**
     * Add edge to graph.
     */
    public void addEdge(Edge edge) throws DuplicateEdgeException{ // 4 0
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
    }

    /**
     * Remove edge from graph.
     */
    public void removeEdge(Edge edge){
        if(!this.inNodesMap.get(edge.outNode).contains(edge.inNode) || !this.outNodesMap.get(edge.inNode).contains(edge.outNode)){
            throw new NullPointerException();
        }

        if(this.inNodesMap.containsKey(edge.outNode)){
            ArrayList<Integer> updatedInNodesList = new ArrayList<>(this.inNodesMap.get(edge.outNode));
            updatedInNodesList.remove((Integer) edge.inNode);
            this.inNodesMap.replace(edge.outNode, updatedInNodesList);
        } else{
            throw new NullPointerException();
        }

        if(this.outNodesMap.containsKey(edge.inNode)){
            ArrayList<Integer> updatedOutNodesList = new ArrayList<>(this.outNodesMap.get(edge.inNode));
            updatedOutNodesList.remove((Integer) edge.outNode);
            this.outNodesMap.replace(edge.inNode, updatedOutNodesList);
        } else{
            throw new NullPointerException();
        }
    }

    /**
     * Add node to graph.
     */
    public void addNode(int node) throws NodeAlreadyExistingException{
        if(!this.inNodesMap.containsKey(node)){
            this.inNodesMap.put(node, new ArrayList<Integer>());
        } else{
            throw new NodeAlreadyExistingException();
        }

        if(!this.outNodesMap.containsKey(node)){
            this.outNodesMap.put(node, new ArrayList<Integer>());
        } else{
            throw new NodeAlreadyExistingException();
        }
    }

    public List<Integer> topologicalSortList() throws GraphHasCyclesException{
        List<Integer> sorted = new LinkedList<>();
        Set<Integer> fullyProcessed = new HashSet<>();
        Set<Integer> inProcess = new HashSet<>();
        Iterator nodesIterator = getSetOfVerticesIterator();
        Integer currentNode;
        boolean ok;
        while(nodesIterator.hasNext()){
            currentNode = (Integer) nodesIterator.next();
            if(!fullyProcessed.contains(currentNode)){
                ok = topoSortDFS(currentNode, sorted, fullyProcessed, inProcess);
                if(!ok){
                    throw new GraphHasCyclesException();
                }
            }
        }
        return sorted;
    }

    private boolean topoSortDFS(Integer currentNode, List<Integer> sorted, Set<Integer> fullyProcessed, Set<Integer> inProcess){
        inProcess.add(currentNode);
        Iterator inNodesOfCurrentNode = getInboundEdgesOfNodeIterator(currentNode);
        Integer currentInNode = -1;
        boolean ok;
        while(inNodesOfCurrentNode.hasNext()){
            currentInNode = ((Edge) inNodesOfCurrentNode.next()).inNode;
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

    /**
     * Remove node from graph and corresponding edges.
     */
    public void removeNode(int node){
        ArrayList<Integer> tempArrayList;
        if(this.outNodesMap.containsKey(node)){
            tempArrayList = new ArrayList(this.outNodesMap.get(node));
            for(Integer inNode :
                    tempArrayList){
                this.inNodesMap.get(inNode).remove((Integer) node);
            }
            this.outNodesMap.remove(node);
        } else{
            throw new NullPointerException("Node doesn't exist");
        }
        if(this.inNodesMap.containsKey(node)){
            tempArrayList = new ArrayList(this.inNodesMap.get(node));
            for(Integer outNode :
                    tempArrayList){
                this.outNodesMap.get(outNode).remove((Integer) node);
            }
            this.inNodesMap.remove(node);
        } else{
            throw new NullPointerException("Node doesn't exist");
        }

    }

    public int getNumberOfEdges(){
        int sum = 0;
        for(Integer node :
                this.inNodesMap.keySet()){
            sum += this.inNodesMap.get(node).size();
        }
        return sum;
    }

    protected Map<Integer, List<Integer>> copy(HashMap<Integer, List<Integer>> originalMap){
        Map<Integer, List<Integer>> copyMap = new LinkedHashMap<>();
        for(Map.Entry<Integer, List<Integer>> entry : originalMap.entrySet()){
            copyMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copyMap;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Directed graph\n");
        sb.append("In nodes map\n");
        for(Integer node :
                inNodesMap.keySet()){
            sb.append(node).append(": ");
            ArrayList<Integer> outNodes = (ArrayList) inNodesMap.get(node);
            for(Integer outNode :
                    outNodes){
                sb.append(outNode).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        sb.append("Out nodes map\n");
        for(Integer node :
                outNodesMap.keySet()){
            sb.append(node).append(": ");
            ArrayList<Integer> inNodes = (ArrayList) outNodesMap.get(node);
            for(Integer inNode :
                    inNodes){
                sb.append(inNode).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        return sb.toString();
    }
}
