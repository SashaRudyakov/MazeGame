package view;

import model.MazeEdgeModel;
import model.MazeGameModel;
import model.MazeNodeModel;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

/**
 * GUI view for {@link MazeGameModel} model.
 */
public class MazeView extends JPanel {
    private Graphics2D g2;
    private static MazeGameModel game;
    private static int size;
    private static int mult;

    public MazeView(MazeGameModel game) {
        this.game = game;
        this.size = game.getSize();
        this.mult = game.getMult();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        // draw edges and traversed nodes
        game.getNodes().forEach(column -> {
            column.forEach(node -> {
                final int x =  node.getX() * mult;
                final int xOffset = x + mult;
                final int y = node.getY() * mult;
                final int yOffset = y + mult;

                if(MazeEdgeModel.drawEdge(node.getLeft())) {
                    g2.drawLine(x, y, x, yOffset);
                }

                if(MazeEdgeModel.drawEdge(node.getRight())) {
                    g2.drawLine(xOffset, y, xOffset, yOffset);
                }

                if(MazeEdgeModel.drawEdge(node.getTop())) {
                    g2.drawLine(x, y, xOffset, y);
                }

                if(MazeEdgeModel.drawEdge(node.getBottom())) {
                    g2.drawLine(x, yOffset, xOffset, yOffset);
                }

                if(node.getToDraw()) {
                    colorNode(node, false);
                }
            });
        });

        final MazeNodeModel currNode = game.getCurrNode();

        // draw curr location
        colorNode(currNode, true);

        if (currNode.getX() == size - 1 && currNode.getY() == size - 1 ) {
            g2.drawString("you win wow so smart very great!", size * mult / 2, size * mult / 2);
        }
    }

    private void colorNode(MazeNodeModel node, boolean isStart) {
        Color color = isStart ? Color.BLUE : Color.CYAN;
        g2.setColor(color);
        g2.fillRect(node.getX() * mult, node.getY() * mult, mult, mult);
        g2.setColor(Color.BLACK);
    }
}
