import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WeightedDirectedGraph extends DirectedGraph {
    private Map<Edge, Integer> weightsMap;

    public WeightedDirectedGraph(Map<Integer, List<Integer>> inNodesMap, Map<Integer, List<Integer>> outNodesMap, Map<Edge, Integer> weightsMap) {
        super(inNodesMap, outNodesMap);
        this.weightsMap = weightsMap;
    }

    public WeightedDirectedGraph copy() {
        Map<Integer, List<Integer>> inNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.inNodesMap);
        Map<Integer, List<Integer>> outNodesMapCopy = copy((HashMap<Integer, List<Integer>>) this.outNodesMap);
        Map<Edge, Integer> weightsMapCopy = copy(this.weightsMap);
        return new WeightedDirectedGraph(inNodesMapCopy, outNodesMapCopy, weightsMapCopy);
    }

    public WeightedDirectedGraph() {
        super();
        this.weightsMap = new LinkedHashMap<>();
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
        String firstLine = fileContent.get(0);
        String[] lineSplit = firstLine.split(" ");
        int numberOfNodes = Integer.valueOf(lineSplit[0]);
        fileContent.remove(0);
        fillMapsWithEmptyLists(numberOfNodes);
        for(String actualLine : fileContent) {
            lineSplit = actualLine.split(" ");
            inNode = Integer.valueOf(lineSplit[0]);
            outNode = Integer.valueOf(lineSplit[1]);
            ArrayList updatedList = new ArrayList(this.inNodesMap.get(outNode));
            updatedList.add(inNode);
            this.inNodesMap.put(outNode, updatedList);
            updatedList = new ArrayList(this.outNodesMap.get(inNode));
            updatedList.add(outNode);
            this.outNodesMap.put(inNode, updatedList);
            this.weightsMap.put(new Edge(inNode, outNode), Integer.valueOf(lineSplit[2]));
        }
    }

    public int getWeight(Edge edge) {
        if(this.weightsMap.containsKey(edge)) {
            return this.weightsMap.get(edge);
        } else {
            throw new NullPointerException("Edge not found");
        }
    }

    public void updateWeight(Edge edge, int newWeight) {
        if(this.weightsMap.containsKey(edge)) {
            this.weightsMap.replace(edge, newWeight);
        } else {
            throw new NullPointerException("Edge not found");
        }
    }

    private Map<Edge, Integer> copy(Map<Edge, Integer> originalMap) {
        Map<Edge, Integer> copyMap = new LinkedHashMap<>();
        for(Map.Entry<Edge, Integer> entry : originalMap.entrySet()) {
            copyMap.put(entry.getKey().copy(), entry.getValue());
        }
        return copyMap;
    }
}
