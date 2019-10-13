package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.controller.Handler;
import game.controller.KeyInput;
import game.model.GameObject;
import game.model.Player;
import ui.Main;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Game extends JPanel implements ActionListener {
    private static Game game;
    private String name;
    private int height;
    private int width;
    private BufferedImage background;
    public static Sprite sprite;
    private boolean isRunning = false;
    private Timer timer;
    private static Handler handler;
    private BufferedImage level;
    private HashMap<GameObject, Integer> players;
    private int numPlayers;

    private Game() {
        name = "ShootingGame";
        width = 1024;
        height = 768;

        sprite = new Sprite();
        handler = new Handler();
        players = new HashMap<>();

        // set bg image to 1.6x by 1.6x scaling
        BufferedImage tempBgImg = sprite.loadImage(this);
        AffineTransform at = new AffineTransform();
        at.scale(1.6, 1.6);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        level = scaleOp.filter(tempBgImg, level);

        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(new KeyInput(handler));
        this.start();
    }

    public static Game getInstance() {
        if (game == null)
            game = new Game();

        return game;
    }

    public Handler getHandler() {
        return this.handler;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWindow(String name, int height, int width){
        this.name = name;
        this.height = height;
        this.width = width;
    }


    private void start() {
        isRunning = true;
        timer = new Timer(10, this);
        timer.start();
    }

    private void stop() {
        timer.start();
    }

    public void tick() {
        this.handler.tick();
    }

    public void paint(Graphics g) {
        // bg
        g.drawImage(level, 0,0,null);

        // renders hp bars
        Main.game.getHandler().render(g);
        for (GameObject go : Main.gameObjects) {
            if (go instanceof Player) {
                // assigns each player a unique player number
                if(!players.containsKey(go)) {
                    players.put(go, players.size() + 1);
                }

                // renders hp bar for each player
                g.setFont(new Font("Arial", 0, 18));
                g.setColor(Color.white.brighter());
                g.drawString("Health Player " + players.get(go) +" : " + go.getId(), 80+ ((players.get(go)-1)*250), Main.game.getHeight() - 100);
                g.setColor(Color.red.darker());
                g.fillRect(80 + ((players.get(go)-1)*250), Main.game.getHeight() - 90, 200, 30);
                g.setColor(Color.green.darker());
                g.fillRect(80 + ((players.get(go)-1)*250), Main.game.getHeight() - 90, ((Player) go).getHealth() * 2, 30);
                g.setColor(Color.white);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (isRunning) {
            tick();
            repaint();
        } else {
            stop();
        }
    }

    public String getName() {
        return this.name;
    }
}
