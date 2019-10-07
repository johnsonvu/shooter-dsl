package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;

    private List<Enemy> enemyList;
    private List<Player> playerList;
    private List<Item> itemList;

    private Game() {
        enemyList = new ArrayList<>();
        playerList = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    public Game getInstance() {
        if (game == null)
            game = new Game();

        return game;
    }

    public void addEnemy(Enemy enemy) {
        enemyList.add(enemy);
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void removeEnemy(Enemy enemy) {
        enemyList.remove(enemy);
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }
}
