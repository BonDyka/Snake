package loc.abondarev.snake;

import javax.swing.JFrame;

/**
 * The class implements main window in app.
 *
 * @author abondarev.
 * @since 25.10.2017.
 */
public class MainWindow extends JFrame { // extend if want to be a window.

    private static final String GAME_NAME = "Snake";

    private static final int WINDOW_WIDTH = 320;
    private static final int WINDOW_HEIGHT = 345;

    public MainWindow() {
        setTitle(GAME_NAME); // set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set operation for close window
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(400, 400);
        setResizable(false);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
