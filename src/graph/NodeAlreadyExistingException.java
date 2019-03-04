package graph;

public class NodeAlreadyExistingException extends Exception {
    public NodeAlreadyExistingException() {}
    public NodeAlreadyExistingException(String message) {
        super(message);
    }
}
