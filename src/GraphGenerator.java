import graph.DirectedGraph;
import graph.DuplicateEdgeException;
import graph.Edge;
import graph.WeightedDirectedGraph;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GraphGenerator {
    public DirectedGraph generateRandomDirectedGraph(int numberOfVertices, int numberOfEdges) throws DuplicateEdgeException {
        DirectedGraph graph = new DirectedGraph(getEmptyMapOfNodes(numberOfVertices), getEmptyMapOfNodes(numberOfVertices));
        generateUnweightedEdges(graph, numberOfVertices, numberOfEdges);
        return graph;
    }

    public WeightedDirectedGraph generateRandomWeightedDirectedGraph(int numberOfVertices, int numberOfEdges, int minimumWeight, int maximumWeight) throws DuplicateEdgeException {
        WeightedDirectedGraph weightedDirectedGraph = new WeightedDirectedGraph(getEmptyMapOfNodes(numberOfVertices), getEmptyMapOfNodes(numberOfVertices), new LinkedHashMap<>());
        generateWeightedGraph(weightedDirectedGraph, numberOfVertices, numberOfEdges, minimumWeight, maximumWeight);
        return weightedDirectedGraph;
    }

    private void generateWeightedGraph(WeightedDirectedGraph weightedDirectedGraph, int numberOfVertices, int numberOfEdges, int minimumWeight, int maximumWeight) throws DuplicateEdgeException{
        ArrayList<Edge> pool = generatePoolOfVertices(numberOfVertices);
        Random random = new Random();
        int nextPick;
        for(int i = 0; i < numberOfEdges; i++){
            nextPick = random.nextInt(pool.size());
            Edge pick = pool.get(nextPick);
            weightedDirectedGraph.addEdge(pick, ThreadLocalRandom.current().nextInt(minimumWeight, maximumWeight + 1));
            pool.remove(nextPick);
        }
    }

    private ArrayList<Edge> generatePoolOfVertices(int numberOfVertices){
        ArrayList<Edge> pool = new ArrayList<>();
        for(int i = 0; i < numberOfVertices; i++){
            for(int j = 0; j < numberOfVertices; j++){
                if(j != i){
                    pool.add(new Edge(i, j));
                }
            }
        }
        return pool;
    }

    private void generateUnweightedEdges(DirectedGraph graph, int numberOfVertices, int numberOfEdges) throws DuplicateEdgeException {
        ArrayList<Edge> pool = generatePoolOfVertices(numberOfVertices);
        Random random = new Random();
        int nextPick;
        for(int i = 0; i < numberOfEdges; i++){
            nextPick = random.nextInt(pool.size());
            graph.addEdge(pool.get(nextPick));
            pool.remove(nextPick);
        }
    }

    private Map<Integer, List<Integer>> getEmptyMapOfNodes(int numberOfNodes) {
        Map<Integer, List<Integer>> tempMap = new LinkedHashMap<>();
        for(int i = 0; i < numberOfNodes; i++) {
            ArrayList<Integer> tempArray = new ArrayList<>();
            tempMap.put(i, tempArray);
        }
        return tempMap;
    }


}
