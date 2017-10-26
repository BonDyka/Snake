package loc.abondarev.snake;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Implements game field.
 *
 * @author abondarev.
 * @since 25.10.2017.
 */
public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 320; // size of game field.
    private final int DOT_SIZE = 16; // size one location (one apple or one segment of snake).
    private final int ALL_DOTS = 400; // amount dots on game field.

    private Image dot;
    private Image apple;

    private int appleX, appleY; // coordinates of apple.

    private int[] x = new int[ALL_DOTS];// current x position of snake
    private int[] y = new int[ALL_DOTS];// current y position of snake
    private int dots;
    // current direction is true.
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean inGame = true;

    private Timer timer;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        addKeyListener(new FieldKeyListener());
        setFocusable(true); // connect key action with game field
        init();
    }

    public void init() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE; // get value between 0 and 20.
        appleY = new Random().nextInt(20) * DOT_SIZE; // get value between 0 and 20.
    }

    private void loadImages() {
        ImageIcon iia = new ImageIcon("resources\\apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("resources\\dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String end = "Game Over";
            Font font = new Font("Times New Roma", 24, Font.BOLD);
            g.setColor(Color.RED);
            g.setFont(font);
            g.drawString(end, 125, SIZE / 2);
        }
    }

    public void move() {
        // relocation snakes tail
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        // relocation snake head depend on direction.
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                return;
            }
        }
        if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    private class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
