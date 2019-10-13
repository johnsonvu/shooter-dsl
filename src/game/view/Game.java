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

    public void makePlayer(String name, int num){
        Player player = new Player(0,0, name, num); // TODO: figure out coordinate mechanic
        playerList.add(player);
    }

    public void makeEnemy(String name, int num){
        Enemy enemy = new Enemy(0,0, name, num); // TODO: figure out coordinate mechanic
        enemyList.add(enemy);
    }

    public void makeProjectile(String name, int num){
        Projectile projectile = new Projectile(0,0, name, num); // TODO: figure out coordinate mechanic
        projectileList.add(projectile);
    }

    public void makeItem(String name, int num){
        Item item = new Item(0,0, name, num); // TODO: figure out coordinate mechanic
        itemList.add(item);
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void removeEnemy(Enemy enemy) {
        enemyList.remove(enemy);
    }

    public void removeProjectile(Projectile projectile) {
        projectileList.remove(projectile);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
