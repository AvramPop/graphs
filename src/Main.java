import graph.WeightedDirectedGraph;

import java.nio.file.FileSystems;

public class Main {

    public static void main(String[] args) {
        WeightedDirectedGraph graph = new WeightedDirectedGraph();
        long startTime = System.nanoTime();
        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/grafuri/src/graph1k.txt"));
        long endTime = System.nanoTime();
        System.out.println("It took " + (((double) endTime - (double) startTime) / 100000000) + " seconds");

//
//        it = graph.getInboundEdgesOfNodeIterator(2);
//        while (it.hasNext()) {
//            graph.Edge edge = (graph.Edge) it.next();
//            System.out.println(edge);
//        }
    }
}
