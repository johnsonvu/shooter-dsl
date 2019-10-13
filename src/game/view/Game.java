package game.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JPanel implements ActionListener {
    private static Game game;

    private String name;
    private int height;
    private int width;

    public static Sprite sprite;

    public Game() {
        name = "ShootingGame";
        height = 800;
        width = 600;

        sprite = new Sprite();
    }

    public static Game getInstance() {
        if (game == null)
            game = new Game();

        return game;
    }

    public void setWindow(String name, int height, int width){
        this.name = name;
        this.height = height;
        this.width = width;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
