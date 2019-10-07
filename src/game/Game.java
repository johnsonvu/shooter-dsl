package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;

    private String name;
    private int height;
    private int width;

    private List<Enemy> enemyList;
    private List<Player> playerList;
    private List<Item> itemList;
    private List<Projectile> projectileList;

    private Game() {
        name = "ShootingGame";
        height = 800;
        width = 600;

        enemyList = new ArrayList<>();
        playerList = new ArrayList<>();
        itemList = new ArrayList<>();
        projectileList = new ArrayList<>();
    }

    public Game getInstance() {
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
        Player player = new Player(name, num);
        playerList.add(player);
    }

    public void makeEnemy(String name, int num){
        Enemy enemy = new Enemy(name, num);
        enemyList.add(enemy);
    }

    public void makeProjectile(String name, int num){
        Projectile projectile = new Projectile(name, num);
        projectileList.add(projectile);
    }

    public void makeItem(String name, int num){
        Item item = new Item(name, num);
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
}
