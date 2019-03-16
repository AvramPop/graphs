import graph.DirectedGraph;
import graph.DuplicateEdgeException;
import graph.Edge;
import graph.WeightedDirectedGraph;

import java.util.*;

public class GraphGenerator {
    public DirectedGraph generateRandomDirectedGraph(int numberOfVertices, int numberOfEdges) throws DuplicateEdgeException {
        DirectedGraph graph = new DirectedGraph(getEmptyMapOfNodes(numberOfVertices), getEmptyMapOfNodes(numberOfVertices));
        generateUnweightedEdges(graph, numberOfEdges);
        return graph;
    }

    private void generateUnweightedEdges(DirectedGraph graph, int numberOfEdges) throws DuplicateEdgeException {
        ArrayList<Edge> pool = new ArrayList<>();
        for(int i = 0; i < numberOfEdges; i++) {
            for(int j = 0; j < numberOfEdges; j++) {
                if(j != i) {
                    pool.add(new Edge(i, j));
                }
            }
        }
        Random random = new Random();
        int nextPick;
        for(int i = 0; i < numberOfEdges; i++) {
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

    private Map<Edge, Integer> generateMapOfWeights(int numberOfNodes) {
        Map<Edge, Integer> tempMap = new LinkedHashMap<>();
        for(int i = 0; i < numberOfNodes; i++) {
            //tempMap.put(i, i);
        }
        return tempMap;
    }



    public WeightedDirectedGraph generateRandomWeightedDirectedGraph(int numberOfVertices, int numberOfEdges) throws DuplicateEdgeException {
        Map<Integer, List<Integer>> inNodesMap = getEmptyMapOfNodes(numberOfVertices);
        Map<Integer, List<Integer>> outNodesMap = getEmptyMapOfNodes(numberOfVertices);
        WeightedDirectedGraph weightedDirectedGraph = new WeightedDirectedGraph();
        generateUnweightedEdges(weightedDirectedGraph, numberOfEdges);
        //generateMapOfWeights()

        return new WeightedDirectedGraph();
    }
}
