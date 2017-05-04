package model;

/**
 * Describes the connection between two nodes.
 */
public class MazeEdgeModel implements Comparable<MazeEdgeModel>{
    private final double value;
    private final MazeNodeModel source;
    private final MazeNodeModel target;
    private boolean connection = false;

    public MazeEdgeModel(MazeNodeModel source, MazeNodeModel target) {
        value = Math.random();
        this.source = source;
        this.target = target;
    }

    public static boolean drawEdge(MazeEdgeModel edge) {
        return edge == null || !edge.getConnection();
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public boolean getConnection() {
        return connection;
    }

    public double getValue() {
        return value;
    }

    public MazeNodeModel getSource() {
        return source;
    }

    public MazeNodeModel getTarget() {
        return target;
    }

    public int compareTo(MazeEdgeModel other) {
        if (value < other.getValue()) {
            return -1;
        }
        else {
            return 1;
        }
    }

    public MazeNodeModel getOtherNode(MazeNodeModel node) {
        if(node.equals(target)) {
            return source;
        }
        else if (node.equals(source)) {
            return target;
        }
        else {
            throw new IllegalArgumentException("Given node is not part of this edge.");
        }
    }
}
