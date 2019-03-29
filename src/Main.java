import graph.DuplicateEdgeException;
import graph.UndirectedGraph;

import java.nio.file.FileSystems;

public class Main {

    public static void main(String[] args) throws DuplicateEdgeException {
//        WeightedDirectedGraph graph = new WeightedDirectedGraph();
//        long startTime = System.nanoTime();
//        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graph.in"));
//        long endTime = System.nanoTime();
//        System.out.println("It took " + (((double) endTime - (double) startTime) / 100000000) + " seconds to load graph from file");
//        System.out.println(graph.toString());
//
//        GraphGenerator graphGenerator = new GraphGenerator();
//        WeightedDirectedGraph graphRandom = graphGenerator.generateRandomWeightedDirectedGraph(10, 20, -500, 500);
//        System.out.println(graphRandom.toString());
//        Console console = new Console(graphRandom);
//        console.run();
        UndirectedGraph graph = new UndirectedGraph(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graphUndirected.in"));
        System.out.println(graph.numberOfSubgraphs());
        graph.subgraphs().forEach(subgraph -> System.out.println(subgraph.toString()));
    }
}
