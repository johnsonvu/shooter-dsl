package game.view;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
    public static final String operatingSystem = System.getProperty("os.name");

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        frame.add(game);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

    }
}