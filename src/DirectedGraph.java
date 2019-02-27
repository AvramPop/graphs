import com.sun.deploy.util.OrderedHashSet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DirectedGraph {
    protected Map<Integer, List<Integer>> inNodesMap;
    protected Map<Integer, List<Integer>> outNodesMap;

    public DirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap) {
        this.inNodesMap = inNodesMap;
        this.outNodesMap = outNodesMap;
    }

    public DirectedGraph() {
        this.inNodesMap = new LinkedHashMap<>();
        this.outNodesMap = new LinkedHashMap<>();
    }

    public void loadGraphFromFile(Path filePath) {
        List<String> fileContent = new ArrayList<>();
        try {
            Files.lines(filePath).forEach(fileContent::add);
        } catch (IOException e) {
            System.out.println("Error while reading");
        }
        int inNode, outNode;
        int numberOfNodes = Integer.valueOf(String.valueOf(fileContent.get(0).charAt(0)));
        fileContent.remove(0);
        fillMapsWithEmptyLists(numberOfNodes);
        for(String actualLine : fileContent) {
            inNode = Integer.valueOf(String.valueOf(actualLine.charAt(0)));
            outNode = Integer.valueOf(String.valueOf(actualLine.charAt(2)));
            ArrayList updatedList = new ArrayList(this.inNodesMap.get(outNode));
            updatedList.add(inNode);
            this.inNodesMap.put(outNode, updatedList);
            updatedList = new ArrayList(this.outNodesMap.get(inNode));
            updatedList.add(outNode);
            this.outNodesMap.put(inNode, updatedList);
        }
    }

    protected void fillMapsWithEmptyLists(int numberOfNodes) {
        List<Integer> emptyList = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++) {
            this.inNodesMap.put(i, emptyList);
            this.outNodesMap.put(i, emptyList);
        }
    }

    public int getNumberOfVertices() {
        return this.inNodesMap.keySet().size();
    }

    public Iterator getSetOfVerticesIterator() {
        return this.inNodesMap.keySet().iterator();
    }

    public boolean hasEdgeBetween(int inNode, int outNode) {
        return this.outNodesMap.get(inNode).contains(outNode);
    }

    public Edge getEdgeBetween(int inNode, int outNode) throws NullPointerException {
        if (hasEdgeBetween(inNode, outNode)) {
            return new Edge(inNode, outNode);
        } else {
            throw new NullPointerException("Edge does not exist");
        }
    }

    public int getInDegreeOfNode(int node) {
        return this.inNodesMap.get(node).size();
    }

    public int getOutDegreeOfNode(int node) {
        return this.outNodesMap.get(node).size();
    }
}
