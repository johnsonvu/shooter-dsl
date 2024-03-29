package game.view;

import evaluate.protoypes.EnemyProto;
import evaluate.protoypes.PlayerProto;
import game.model.Enemy;
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
        Main.game = Game.getInstance();
        Player player = new Player(new PlayerProto("Leo", 100, 100), "Leo");
        Player player2 = new Player(new PlayerProto("Justina", 100, 100), "Justina");
        Player player3 = new Player(new PlayerProto("Bob", 100, 100), "Bob");
        Enemy enemy = new Enemy(new EnemyProto("jkwansa", 100, 100), "jkwansa");
        Main.gameObjects.add(player);
        Main.gameObjects.add(player2);
        Main.gameObjects.add(player3);
        Main.gameObjects.add(enemy);
        new Window(Game.getInstance().getWidth(), Game.getInstance().getHeight(), Game.getInstance().getName(), Game.getInstance());
    }
}