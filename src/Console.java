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
            System.out.println("Menu");
            System.out.println("1 - number of vertices");
            System.out.println("2 - set of vertices");
            System.out.println("3 e1 e2 - edge between e1 and e2");
            System.out.println("4 v - in degree of v");
            System.out.println("5 v - out degree of v");
            System.out.println("6 v - outbound edges of e");
            System.out.println("7 v - inbound edges of e");
            System.out.println("8 e - weight of e");
            System.out.println("9 e w - add edge e with weight w");
            System.out.println("10 e - remove edge e");
            System.out.println("11 v - add vertex v");
            System.out.println("12 v - remove vertex v");
            System.out.println("13 - copy current graph");
            System.out.println("14 e w - update edge e to weight w");
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
                    try{
                        System.out.println("in degree");
                        System.out.println(graph.getInDegreeOfNode(Integer.valueOf(userInput[1])));
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                } else if (userInput[0].equals("5")) {
                    try{
                        System.out.println("out degree");
                        System.out.println(graph.getOutDegreeOfNode(Integer.valueOf(userInput[1])));
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                } else if (userInput[0].equals("6")) {
                    try{
                        System.out.println("outbound edges");
                        Iterator graphIterator = graph.getOutboundEdgesOfNodeIterator(Integer.valueOf(userInput[1]));
                        while(graphIterator.hasNext()){
                            System.out.println(graphIterator.next());
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                } else if (userInput[0].equals("7")) {
                    try{
                        System.out.println("inbound edges");
                        Iterator graphIterator = graph.getInboundEdgesOfNodeIterator(Integer.valueOf(userInput[1]));
                        while(graphIterator.hasNext()){
                            System.out.println(graphIterator.next());
                        }
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                } else if (userInput[0].equals("8")) {
                    System.out.println("weight");
                    try{
                        System.out.println(graph.getWeight(new Edge(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2]))));
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
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
                } else if (userInput[0].equals("14")) {
                    System.out.println("update weight");
                    try{
                        graph.updateWeight(new Edge(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2])), Integer.valueOf(userInput[3]));
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("wrong input");
                }
            }
        }
    }
}
