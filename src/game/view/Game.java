package game.view;

import game.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends JPanel implements ActionListener {
    private static Game game;

    private String name;
    private int height;
    private int width;

    private Map<String, GameObject> masterTable;
    private List<Enemy> enemyList;
    private List<Player> playerList;
    private List<Item> itemList;
    private List<Projectile> projectileList;

    public Game() {
        name = "ShootingGame";
        height = 800;
        width = 600;

        masterTable = new HashMap<>();
        enemyList = new ArrayList<>();
        playerList = new ArrayList<>();
        itemList = new ArrayList<>();
        projectileList = new ArrayList<>();
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
        masterTable.put(name, player);
    }

    public void makeEnemy(String name, int num){
        Enemy enemy = new Enemy(0,0, name, num); // TODO: figure out coordinate mechanic
        enemyList.add(enemy);
        masterTable.put(name, enemy);
    }

    public void makeProjectile(String name, int num){
        Projectile projectile = new Projectile(0,0, name, num); // TODO: figure out coordinate mechanic
        projectileList.add(projectile);
        masterTable.put(name, projectile);
    }

    public void makeItem(String name, int num){
        Item item = new Item(0,0, name, num); // TODO: figure out coordinate mechanic
        itemList.add(item);
        masterTable.put(name, item);
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

    public void removeGameObject(String name) {
        GameObject obj = masterTable.get(name);
        masterTable.remove(name);
        if (obj instanceof Player) {
            playerList.remove(obj);
        } else if (obj instanceof Enemy) {
            enemyList.remove(obj);
        } else if (obj instanceof Projectile) {
            projectileList.remove(obj);
        } else {
            itemList.remove(obj);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
