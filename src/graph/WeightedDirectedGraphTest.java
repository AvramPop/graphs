package graph;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WeightedDirectedGraphTest {
    WeightedDirectedGraph graph = new WeightedDirectedGraph();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        graph.loadGraphFromFile(FileSystems.getDefault().getPath("/home/dani/Desktop/code/faculta/an1/sem2/grafuri/data/graph2.in"));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        graph = null;
    }

    @org.junit.jupiter.api.Test
    void getNumberOfVertices() throws NodeAlreadyExistingException {
        assertEquals(graph.getNumberOfVertices(), 6);
        graph.addNode(10);
        assertEquals(graph.getNumberOfVertices(), 7);
        assertThrows(NodeAlreadyExistingException.class, () -> graph.addNode(0));
    }

    @org.junit.jupiter.api.Test
    void getSetOfVerticesIterator() {
        ArrayList<Integer> testArrayList = new ArrayList<>();
        testArrayList.add(0);
        testArrayList.add(1);
        testArrayList.add(2);
        testArrayList.add(3);
        testArrayList.add(4);
        testArrayList.add(5);
        Iterator graphIterator = graph.getSetOfVerticesIterator();
        Iterator listIterator = testArrayList.iterator();
        while(graphIterator.hasNext()) {
            assertEquals(graphIterator.next(), listIterator.next());
        }

    }

    @org.junit.jupiter.api.Test
    void hasEdgeBetween() {
        assertTrue(graph.hasEdgeBetween(0, 2));
        assertFalse(graph.hasEdgeBetween(0, 10));
        assertFalse(graph.hasEdgeBetween(2, 3));
    }

    @org.junit.jupiter.api.Test
    void getEdgeBetween() {
        assertEquals(graph.getEdgeBetween(0, 2), new Edge(0, 2));
        assertThrows(NullPointerException.class, () -> graph.getEdgeBetween(10, 20));
    }

    @org.junit.jupiter.api.Test
    void getInDegreeOfNode() {
        assertEquals(graph.getInDegreeOfNode(0), 2);
    }

    @org.junit.jupiter.api.Test
    void getOutDegreeOfNode() {
        assertEquals(graph.getOutDegreeOfNode(0), 2);
    }

    @org.junit.jupiter.api.Test
    void getOutboundEdgesOfNodeIterator() {
        ArrayList<Edge> testArrayList = new ArrayList<>();
        testArrayList.add(new Edge(2, 0));
        testArrayList.add(new Edge(2, 1));
        Iterator listIterator = testArrayList.iterator();
        Iterator graphIterator = graph.getOutboundEdgesOfNodeIterator(2);
        while (graphIterator.hasNext()) {
            assertEquals(graphIterator.next(), listIterator.next());
        }
    }

    @org.junit.jupiter.api.Test
    void getInboundEdgesOfNodeIterator() {
        ArrayList<Edge> testArrayList = new ArrayList<>();
        testArrayList.add(new Edge(2, 0));
        testArrayList.add(new Edge(1, 0));
        Iterator listIterator = testArrayList.iterator();
        Iterator graphIterator = graph.getInboundEdgesOfNodeIterator(0);
        while (graphIterator.hasNext()) {
            assertEquals(graphIterator.next(), listIterator.next());
        }
    }

    @org.junit.jupiter.api.Test
    void addEdge() throws DuplicateEdgeException {
        assertEquals(graph.getInDegreeOfNode(3), 1);
        graph.addEdge(new Edge(2, 3));
        assertEquals(graph.getEdgeBetween(2, 3), new Edge(2, 3));
        assertEquals(graph.getInDegreeOfNode(3), 2);
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        assertEquals(graph.getNumberOfEdges(), 9);
        graph.removeEdge(new Edge(5, 3));
        assertEquals(graph.getNumberOfEdges(), 8);
        assertThrows(NullPointerException.class, () -> graph.getEdgeBetween(5, 3));
        assertThrows(NullPointerException.class, () -> graph.getEdgeBetween(10, 3));
    }

    @org.junit.jupiter.api.Test
    void addNode() throws NodeAlreadyExistingException {
        assertEquals(graph.getNumberOfVertices(), 6);
        graph.addNode(10);
        assertEquals(graph.getNumberOfVertices(), 7);
        assertEquals(graph.getInDegreeOfNode(10), 0);
        assertEquals(graph.getOutDegreeOfNode(10), 0);
    }

    @org.junit.jupiter.api.Test
    void removeNode() {
        assertEquals(graph.getNumberOfVertices(), 6);
        assertEquals(graph.getNumberOfEdges(), 9);
        graph.removeNode(0);
        assertEquals(graph.getNumberOfVertices(), 5);
        assertEquals(graph.getNumberOfEdges(), 5);
        assertThrows(NullPointerException.class, () -> graph.getWeight(new Edge(0, 1)));
    }

    @org.junit.jupiter.api.Test
    void copy() {
        graph.WeightedDirectedGraph graph2 = graph.copy();
        assertEquals(graph.getWeight(new graph.Edge(5, 3)), 8);
        assertEquals(graph2.getWeight(new graph.Edge(5, 3)), 8);
        graph.updateWeight(new graph.Edge(5, 3), 100);
        assertEquals(graph.getWeight(new graph.Edge(5, 3)), 100);
        assertEquals(graph2.getWeight(new graph.Edge(5, 3)), 8);
        graph2.updateWeight(new graph.Edge(5, 3), 10000);
        assertEquals(graph.getWeight(new graph.Edge(5, 3)), 100);
        assertEquals(graph2.getWeight(new graph.Edge(5, 3)), 10000);
    }

    @org.junit.jupiter.api.Test
    void getWeight() {
        assertEquals(graph.getWeight(new Edge(5, 3)), 8);
        assertThrows(NullPointerException.class, () -> graph.getWeight(new Edge(100, 105)));
    }

    @org.junit.jupiter.api.Test
    void updateWeight() {
        assertEquals(graph.getWeight(new Edge(5, 3)), 8);
        graph.updateWeight(new Edge(5, 3), 100);
        assertEquals(graph.getWeight(new Edge(5, 3)), 100);
        assertThrows(NullPointerException.class, () -> graph.updateWeight(new Edge(100, 105), 50));

    }
}