package game.view;

import game.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements ActionListener {
    private static Game game;

    private String name;
    private int height;
    private int width;

    private List<Enemy> enemyList;
    private List<Player> playerList;
    private List<Item> itemList;
    private List<Projectile> projectileList;
    public static Sprite sprit;

    public Game() {
        name = "ShootingGame";
        height = 800;
        width = 600;

        enemyList = new ArrayList<>();
        playerList = new ArrayList<>();
        itemList = new ArrayList<>();
        projectileList = new ArrayList<>();
        sprit = new Sprite();
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
