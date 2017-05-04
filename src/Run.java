import controller.MazeKeyListener;
import model.MazeGameModel;
import view.MazeView;

import javax.swing.JFrame;

/**
 * Construct the GUI view, model, and controller to play maze game.
 */
public class Run {

    public static void main(String[] args) {
        int size = 100;
        int mult = 600 / size;

        JFrame frame = new JFrame();
        frame.setSize(mult * size, mult * size + 20);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MazeGameModel game = new MazeGameModel(size, mult);
        MazeView view = new MazeView(game);
        frame.addKeyListener(new MazeKeyListener(game, view));
        frame.setContentPane(view);

        frame.setVisible(true);
        frame.invalidate();
    }
}
