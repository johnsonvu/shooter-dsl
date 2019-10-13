package game.view;

import evaluate.protoypes.PlayerProto;
import game.model.Player;
import ui.Main;

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
        Main.game = new Game();
        Player player = new Player(new PlayerProto("jkwansa", 100, 100), "jkwansa");
        Main.game.getHandler().objects.add(player);

        new Window(1200, 800, "TEST GAME!", Main.game);
    }
}