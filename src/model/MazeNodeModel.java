package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Represents a single cell in the maze.
 */
public class MazeNodeModel {

    private MazeNodeModel parent;
    private List<MazeNodeModel> children;
    private MazeEdgeModel left;
    private MazeEdgeModel right;
    private MazeEdgeModel top;
    private MazeEdgeModel bottom;
    private boolean toDraw = false;
    private boolean isTraversed = false;
    private final int x;
    private final int y;

    public MazeNodeModel(MazeEdgeModel left, MazeEdgeModel right, MazeEdgeModel top, MazeEdgeModel bottom, int x, int y) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.x = x;
        this.y = y;
        this.parent = this;
        children = new ArrayList<>();
        addChild(this);
    }

    public static MazeNodeModel createEmptyMazeNode(int x, int y) {
        return new MazeNodeModel(null, null, null, null, x, y);
    }

    public void setToDraw(boolean traversed) {
        this.toDraw = traversed;
    }

    public void setIsTraversed(boolean traversed) {
        this.isTraversed = traversed;
    }

    public boolean getIsTraversed() {
        return this.isTraversed;
    }

    public boolean getToDraw() {
        return this.toDraw;
    }

    public void setParent(MazeNodeModel parent) {
        this.parent = parent;
    }

    public MazeNodeModel getParent() {
        return parent;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public MazeEdgeModel getLeft() {
        return left;
    }

    public MazeEdgeModel getRight() {
        return right;
    }

    public MazeEdgeModel getTop() {
        return top;
    }

    public MazeEdgeModel getBottom() {
        return bottom;
    }

    public void setLeft(MazeEdgeModel left) {
        this.left = left;
    }

    public void setRight(MazeEdgeModel right) {
        this.right = right;
    }

    public void setTop(MazeEdgeModel top) {
        this.top = top;
    }

    public void setBottom(MazeEdgeModel bottom) {
        this.bottom = bottom;
    }

    public void addChild(MazeNodeModel node) {
        children.add(node);
    }

    public List<MazeNodeModel> getChildren() {
        return this.children;
    }

    public List<MazeEdgeModel> getAllEdges() {
        return new ArrayList<MazeEdgeModel>() {{
            add(left);
            add(right);
            add(top);
            add(bottom);
        }};
    }

    public List<MazeEdgeModel> getEdges(boolean connected) {
        return getAllEdges().stream().filter(edge -> {
            return edge != null && (edge.getConnection() == connected);
        }).collect(Collectors.toList());
    }
}
