public class Edge {
    public final int inNode;
    public final int outNode;

    public Edge(int inNode, int outNode){
        this.inNode = inNode;
        this.outNode = outNode;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Edge otherEdge) {
        return this.inNode == otherEdge.inNode && this.outNode == otherEdge.outNode;
    }

    @Override
    public String toString() {
        return String.valueOf(inNode) + " " + String.valueOf(outNode);
    }
}
