import graph.WeightedDirectedGraph;

import java.nio.file.FileSystems;

public class Main {

    public static void main(String[] args) {
        WeightedDirectedGraph graph = new WeightedDirectedGraph();
        long startTime = System.nanoTime();
        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/grafuri/src/graph1m.txt"));
        long endTime = System.nanoTime();
        System.out.println("It took " + (((double) endTime - (double) startTime) / 100000000) + " seconds");
//        System.out.println(graph.getNumberOfVertices());
//        Iterator it = graph.getSetOfVerticesIterator();
//        while(it.hasNext()) {
//            int next = (int) it.next();
//            System.out.println(next);
//        }
//        System.out.println(graph.hasEdgeBetween(2, 3));
//        System.out.println(graph.getEdgeBetween(2, 1));
//
//        System.out.println(graph.getInDegreeOfNode(3));
//        System.out.println(graph.getOutDegreeOfNode(2));
//
//        Iterator it = graph.getOutboundEdgesOfNodeIterator(2);
//        while (it.hasNext()) {
//            graph.Edge edge = (graph.Edge) it.next();
//            System.out.println(edge);
//        }
//
//        it = graph.getInboundEdgesOfNodeIterator(2);
//        while (it.hasNext()) {
//            graph.Edge edge = (graph.Edge) it.next();
//            System.out.println(edge);
//        }
//
//        graph.WeightedDirectedGraph graph2 = graph.copy();
//        System.out.println(graph.getWeight(new graph.Edge(5, 3)));
//        System.out.println(graph2.getWeight(new graph.Edge(5, 3)));
//        graph.updateWeight(new graph.Edge(5, 3), 100);
//        System.out.println(graph.getWeight(new graph.Edge(5, 3)));
//        System.out.println(graph2.getWeight(new graph.Edge(5, 3)));
//        graph2.updateWeight(new graph.Edge(5, 3), 10000);
//        System.out.println(graph.getWeight(new graph.Edge(5, 3)));
//        System.out.println(graph2.getWeight(new graph.Edge(5, 3)));

        //System.out.println("loaded successfully");
////        graph.updateWeight(new graph.Edge(5, 3), 7);
//        System.out.println(graph.getWeight(new graph.Edge(5, 3)));
    }
}
