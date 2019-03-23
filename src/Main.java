import graph.DuplicateEdgeException;
import graph.WeightedDirectedGraph;

import java.nio.file.FileSystems;

public class Main {

    public static void main(String[] args) throws DuplicateEdgeException {
        WeightedDirectedGraph graph = new WeightedDirectedGraph();
        long startTime = System.nanoTime();
        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graph.in"));
        long endTime = System.nanoTime();
        System.out.println("It took " + (((double) endTime - (double) startTime) / 100000000) + " seconds to load graph from file");
        System.out.println(graph.toString());
        Console console = new Console(graph);
        console.run();
//        GraphGenerator graphGenerator = new GraphGenerator();
//        DirectedGraph graphRandom = graphGenerator.generateRandomDirectedGraph(10, 50);
//        System.out.println(graphRandom.toString());
//        System.out.println();
//        WeightedDirectedGraph graphRandom2 = graphGenerator.generateRandomWeightedDirectedGraph(20, 50, -1000, 1000);
//        System.out.println(graphRandom2.toString());
    }
}
