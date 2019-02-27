import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        DirectedGraph graph = new WeightedDirectedGraph();
        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/grafuri/src/graph.in"));
        System.out.println(graph.getNumberOfVertices());
        Iterator it = graph.getSetOfVerticesIterator();
        while(it.hasNext()) {
            int next = (int) it.next();
            System.out.println(next);
        }
        System.out.println(graph.hasEdgeBetween(2, 3));
        System.out.println(graph.getEdgeBetween(2, 1));

        System.out.println(graph.getInDegreeOfNode(3));
        System.out.println(graph.getOutDegreeOfNode(2));
    }
}
