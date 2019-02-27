import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WeightedDirectedGraph extends DirectedGraph {
    private Map<Edge, Integer> costsMap;

    public WeightedDirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap, Map<Edge, Integer> costsMap) {
        super(inNodesMap, outNodesMap);
        this.costsMap = costsMap;
    }

    public WeightedDirectedGraph() {
        super();
        this.costsMap = new LinkedHashMap<>();
    }

    @Override
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
            this.costsMap.put(new Edge(inNode, outNode), Integer.valueOf(String.valueOf(actualLine.charAt(4))));
        }
    }
}
