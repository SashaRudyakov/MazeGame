package model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Model representing maze game board.
 */
public class MazeGameModel {

    private static final List<List<MazeNodeModel>> MAZE = new ArrayList<>();
    private static final List<MazeEdgeModel> EDGES = new ArrayList<>();
    private static int mult;
    private static int size;
    private static boolean foundSolution = false;
    private MazeNodeModel currNode;

    public MazeGameModel(int size, int mult) {
        this.size = size;
        this.mult = mult;
        generateMaze();
    }

    public List<List<MazeNodeModel>> getNodes() {
        return MAZE;
    }

    public MazeNodeModel getCurrNode() {
        return currNode;
    }

    public int getMult() {
        return mult;
    }

    public int getSize() {
        return size;
    }

    public void move(MazeEdgeModel edge, int x, int y) {
        if (edge != null && edge.getConnection()) {
            currNode = MAZE.get(x).get(y);
            currNode.setToDraw(true);
        }
    }

    /**
     * Solve maze using modified BFS.
     */
    public void solveMaze() {
        final MazeNodeModel source = MAZE.get(0).get(0);
        bfs(source);
    }

    /**
     * Generate the maze.
     */
    private void generateMaze() {

        generateNodes();

        generateEdges();

        Collections.sort(EDGES);

        generateConnections();

        currNode = MAZE.get(0).get(0);
        currNode.setToDraw(true);
    }

    /**
     * Generate nodes.
     */
    private void generateNodes() {
        for(int i = 0; i < size; i++) {
            List<MazeNodeModel> column = new ArrayList<>();
            for(int j = 0; j < size; j++) {
                column.add(MazeNodeModel.createEmptyMazeNode(i, j));
            }
            MAZE.add(column);
        }
    }

    /**
     * Generate edges.
     */
    private void generateEdges() {
        MAZE.forEach(column -> {
            column.forEach(sourceNode -> {
                final int x = sourceNode.getX();
                final int y = sourceNode.getY();

                final MazeNodeModel topNode = y == 0 ? null : MAZE.get(x).get(y - 1);
                final MazeNodeModel bottomNode = (y == (MAZE.size() - 1)) ? null : MAZE.get(x).get(y + 1);
                final MazeNodeModel leftNode = (x == 0) ? null : MAZE.get(x - 1).get(y);
                final MazeNodeModel rightNode = (x == (MAZE.size() - 1)) ? null : MAZE.get(x + 1).get(y);

                if(topNode != null && topNode.getBottom() == null && sourceNode.getTop() == null) {
                    final MazeEdgeModel topEdge = new MazeEdgeModel(sourceNode, topNode);
                    sourceNode.setTop(topEdge);
                    topNode.setBottom(topEdge);
                    EDGES.add(topEdge);
                }
                if(bottomNode != null && bottomNode.getTop() == null && sourceNode.getBottom() == null) {
                    final MazeEdgeModel bottomEdge = new MazeEdgeModel(sourceNode, bottomNode);
                    sourceNode.setBottom(bottomEdge);
                    bottomNode.setTop(bottomEdge);
                    EDGES.add(bottomEdge);
                }
                if(leftNode != null && leftNode.getRight() == null && sourceNode.getLeft() == null) {
                    final MazeEdgeModel leftEdge = new MazeEdgeModel(sourceNode, leftNode);
                    sourceNode.setLeft(leftEdge);
                    leftNode.setRight(leftEdge);
                    EDGES.add(leftEdge);
                }
                if(rightNode != null && rightNode.getLeft() ==  null && sourceNode.getRight() == null) {
                    final MazeEdgeModel rightEdge = new MazeEdgeModel(sourceNode, rightNode);
                    sourceNode.setRight(rightEdge);
                    rightNode.setLeft(rightEdge);
                    EDGES.add(rightEdge);
                }
            });
        });
    }

    /**
     * Perform Kruskal's algorithm to generate connections.
     */
    private void generateConnections() {
        EDGES.forEach(edge -> {
            final MazeNodeModel target = edge.getTarget();
            final MazeNodeModel source = edge.getSource();

            if (!(source.getParent().equals(target.getParent()))) {
                edge.setConnection(true);

                target.getParent().getChildren().forEach(node -> {
                    node.setParent(edge.getSource().getParent());
                    edge.getSource().getParent().addChild(node);
                });
            }
        });
    }

    private void bfs(MazeNodeModel current) {
        if (foundSolution || current.getIsTraversed()) {
            return;
        }
        current.setIsTraversed(true);
        current.setToDraw(true);

        if (current.equals(MAZE.get(size - 1).get(size - 1))) {
            foundSolution = true;
            return;
        }
        else {
            final List<MazeEdgeModel> connectedEdges = current.getEdges(true);
            connectedEdges.forEach(edge -> {
                backtrack(current);
                bfs(edge.getSource());
                bfs(edge.getTarget());
            });
        }
    }

    private void backtrack(MazeNodeModel node) {
        final List<MazeNodeModel> nodes = findPreviousNodes(node);
        if (nodes.size() == 1) {
            final MazeNodeModel prev = nodes.get(0);
            if (prev.getIsTraversed()) {
                node.setToDraw(false);
                backtrack(prev);
            }
        }
    }

    private List<MazeNodeModel> findPreviousNodes(MazeNodeModel node) {
        final List<MazeEdgeModel> allEdges = node.getEdges(true);
        final List<MazeNodeModel> nodes = new ArrayList<>();

        allEdges.forEach(edge -> {
            final MazeNodeModel target = edge.getOtherNode(node);
            if (!target.equals(node) && !(!target.getToDraw() && target.getIsTraversed())) {
                nodes.add(target);
            }
        });

        return nodes;
    }
}

