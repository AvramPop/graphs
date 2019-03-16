import graph.DuplicateEdgeException;
import graph.Edge;
import graph.NodeAlreadyExistingException;
import graph.WeightedDirectedGraph;

import java.util.Iterator;
import java.util.Scanner;

public class Console {
    private WeightedDirectedGraph graph;
    public Console(WeightedDirectedGraph graph){
        this.graph = graph;
    }

    public void run() {
        Scanner keyboard = new Scanner(System.in);
        while(true) {
            System.out.println(">");
            String[] userInput = keyboard.nextLine().split(" ");
            if(userInput.length != 0) {
                if(userInput[0].equals("exit")) {
                    System.out.println("Bye!");
                    break;
                } else if (userInput[0].equals("1")) {
                    System.out.println("number of vertices");
                    System.out.println(graph.getNumberOfVertices());
                } else if (userInput[0].equals("2")) {
                    System.out.println("print set of vertices");
                    Iterator graphIterator = graph.getSetOfVerticesIterator();
                    while(graphIterator.hasNext()) {
                        System.out.println(graphIterator.next());
                    }
                } else if (userInput[0].equals("3")) {
                    System.out.println("edge between");
                    if(graph.hasEdgeBetween(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2]))){
                        System.out.println(graph.getEdgeBetween(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2])));
                    } else {
                        System.out.println("no edge");
                    }
                } else if (userInput[0].equals("4")) {
                    System.out.println("in degree");
                    System.out.println(graph.getInDegreeOfNode(Integer.valueOf(userInput[1])));
                } else if (userInput[0].equals("5")) {
                    System.out.println("out degree");
                    System.out.println(graph.getOutDegreeOfNode(Integer.valueOf(userInput[1])));
                } else if (userInput[0].equals("6")) {
                    System.out.println("outbound edges");
                    Iterator graphIterator = graph.getOutboundEdgesOfNodeIterator(Integer.valueOf(userInput[1]));
                    while(graphIterator.hasNext()) {
                        System.out.println(graphIterator.next());
                    }
                } else if (userInput[0].equals("7")) {
                    System.out.println("inbound edges");
                    Iterator graphIterator = graph.getInboundEdgesOfNodeIterator(Integer.valueOf(userInput[1]));
                    while(graphIterator.hasNext()) {
                        System.out.println(graphIterator.next());
                    }
                } else if (userInput[0].equals("8")) {
                    System.out.println("weight");
                    System.out.println(graph.getWeight(new Edge(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2]))));
                } else if (userInput[0].equals("9")) {
                    System.out.println("add edge");
                    try{
                        graph.addEdge(new Edge(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2])), Integer.valueOf(userInput[3]));
                        System.out.println(graph.toString());
                    } catch(DuplicateEdgeException e){
                        System.out.println("Edge already existing");
                    }
                } else if (userInput[0].equals("10")) {
                    System.out.println("remove edge");
                    try{
                        graph.removeEdge(new Edge(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2])));
                        System.out.println(graph.toString());
                    } catch(Exception e){
                        System.out.println("edge doesn't exist");
                    }
                } else if (userInput[0].equals("11")) {
                    System.out.println("add vertex");
                    try{
                        graph.addNode(Integer.valueOf(userInput[1]));
                        System.out.println(graph.toString());
                    } catch(NodeAlreadyExistingException e){
                        System.out.println("node existing");
                    }
                } else if (userInput[0].equals("12")) {
                    System.out.println("remove vertex");
                    try{
                        graph.removeNode(Integer.valueOf(userInput[1]));
                        System.out.println(graph.toString());
                    } catch(Exception e){
                        System.out.println("vertex not existing");
                    }
                } else if (userInput[0].equals("13")) {
                    System.out.println("copy graph");
                    WeightedDirectedGraph graph2 = graph.copy();
                    System.out.println(graph2.toString());
                } else {
                    System.out.println("wrong input");
                }
            }
        }
    }
}
