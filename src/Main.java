import graph.ActivitiesGraph;
import graph.DuplicateEdgeException;
import graph.GraphHasCyclesException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws DuplicateEdgeException {
//        WeightedDirectedGraph graph = new WeightedDirectedGraph();
//        long startTime = System.nanoTime();
//        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graph.in"));
//        long endTime = System.nanoTime();
//        System.out.println("It took " + (((double) endTime - (double) startTime) / 100000000) + " seconds to load graph from file");
//        System.out.println(graph.toString());
//
//        WeightedDirectedGraph graph = new WeightedDirectedGraph();
//        graph.loadGraphFromFile(getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graphForMultiplication"));
//        System.out.println(graph.toString());
//        try{
//            System.out.println("minimum cost: " + graph.lowestCostWalk(1, 3));
//        } catch(NegativeCyclesException e){
//            System.err.println("Graph has negative cycles!");
//        }
//        Console console = new Console(graphRandom);
//        console.run();
//        UndirectedGraph graph = new UndirectedGraph(getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graph100k.txt"));
//        System.out.println("Number of subgraphs:" + graph.numberOfSubgraphs());
//        graph.subgraphs().forEach(subgraph -> System.out.println(subgraph.toString()));
//        WeightedDirectedGraph graph = new WeightedDirectedGraph();
//        graph.loadGraphFromFile(getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graphForMultiplication"));
//        System.out.println(graph.toString());
//        printMatrix(graph.shortestWalksMatrix(false));
//        DirectedGraph graph = new DirectedGraph();
//        graph.loadGraphFromFile(getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graph1k.txt"));
//        System.out.println(graph.toString());
//        try{
//            List<Integer> result = graph.topologicalSortList();
//            result.forEach(System.out::println);
//        } catch(GraphHasCyclesException e){
//            System.err.println("we have cycles!");
//        }
//        Console console = new Console((WeightedDirectedGraph) graph);
//        console.run();
        ActivitiesGraph graph = new ActivitiesGraph(getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/activitiesGraph.txt"));
        System.out.println(graph.toString());
        try{
            List<ActivitiesGraph.Node> result = graph.topologicalSortList();
            result.forEach(System.out::println);
        } catch(GraphHasCyclesException e){
            System.err.println("we have cycles!");
        }

        try{
            graph.generateOrdering();
        } catch(GraphHasCyclesException e){
            System.err.println("we have cycles!");
        }
        System.out.println(graph.toString());
        System.out.println("Total time: " + graph.getNodesList().get(graph.getNodesList().size() - 1).getLatestStartingTime());
        System.out.print("Critical activities: ");
        for(ActivitiesGraph.Node node : graph.getNodesList()){
            if(node.getLatestFinishTime() == node.getEarliestFinishTime()){
                System.out.print(node.getName() + ", ");
            }
        }
    }

    private static Path getPath(String path){
        return FileSystems.getDefault().getPath(path);
    }

    private static void printMatrix(double[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
