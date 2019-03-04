package graph;

import java.util.Objects;

public class Edge {
    public final int inNode;
    public final int outNode;

    public Edge(int inNode, int outNode){
        this.inNode = inNode;
        this.outNode = outNode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.inNode, this.outNode);
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Edge)) {
            return false;
        }

        Edge edge = (Edge) o;

        return this.inNode == edge.inNode && this.outNode == edge.outNode;

    }

    public Edge copy() {
        return new Edge(this.inNode, this.outNode);
    }

    public boolean containsNode(int node) {
        return inNode == node || outNode == node;
    }

    @Override
    public String toString() {
        return "graph.Edge " + String.valueOf(inNode) + " to " + String.valueOf(outNode);
    }
}
