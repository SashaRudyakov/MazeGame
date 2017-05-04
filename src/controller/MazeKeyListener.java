package controller;

import model.MazeGameModel;
import model.MazeNodeModel;
import view.MazeView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by sasharudyakov on 4/23/17.
 */
public class MazeKeyListener implements KeyListener {
    private MazeGameModel game;
    private MazeView view;

    public MazeKeyListener(MazeGameModel game, MazeView view) {
        this.game = game;
        this.view = view;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final MazeNodeModel currNode = game.getCurrNode();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.move(currNode.getRight(), currNode.getX() + 1, currNode.getY());
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.move(currNode.getLeft(), currNode.getX() - 1, currNode.getY());
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            game.move(currNode.getTop(), currNode.getX(), currNode.getY() - 1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            game.move(currNode.getBottom(), currNode.getX(), currNode.getY() + 1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            game.solveMaze();
        }
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
