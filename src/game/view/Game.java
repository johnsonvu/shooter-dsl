package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import evaluate.protoypes.GameObjectProto;
import game.controller.Audio;
import game.controller.Handler;
import game.controller.KeyInput;
import game.model.Enemy;
import game.model.GameObject;
import game.model.Player;
import ui.Main;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.stream.Collectors;

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
    private Audio audio;
    private boolean endGame = false;
    private boolean winGame = false;

    private Game() {
        name = "ShootingGame";
        width = 2000;
        height = 2000;

        sprite = new Sprite();
        handler = new Handler();
        players = new HashMap<>();
        audio = new Audio("media/Audio/BG/1.wav");
        audio.setLoop(true);

        // scale image
        BufferedImage tempBgImg = sprite.loadImage(this);
        AffineTransform at = new AffineTransform();
        double scaleX = (double) width/640;
        double scaleY = (double) height/480;
        at.scale(scaleX, scaleY);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        level = scaleOp.filter(tempBgImg, level);

        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(new KeyInput(handler));
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


    public void start() {
        isRunning = true;
        timer = new Timer(15, this);
        timer.start();
        audio.play();
    }

    private void stop() {
        timer.start();
    }

    public void tick() {
        this.handler.tick();
        checkEndGame();
        checkWinGame();
    }

    public void paint(Graphics g) {
        // bg
        g.drawImage(level, 0,0,null);

        // game objects
        Game.getInstance().getHandler().render(g);

        // hp bars
        for (GameObject go : Main.gameObjects) {
            if (go instanceof Player) {
                // assigns each player a unique player number
                if(!players.containsKey(go)) {
                    players.put(go, players.size() + 1);
                }

                // renders hp bar for each player
                g.setFont(new Font("Arial", 0, 18));
                g.setColor(Color.white.brighter());
                g.drawString("Health Player " + players.get(go) +" : " + go.getId(), 80+ ((players.get(go)-1)*250), Game.getInstance().getHeight() - 100);
                g.setColor(Color.red.darker());
                g.fillRect(80 + ((players.get(go)-1)*250), Game.getInstance().getHeight() - 90, 200, 30);
                g.setColor(Color.green.darker());
                g.fillRect(80 + ((players.get(go)-1)*250), Game.getInstance().getHeight() - 90, 200*((Player) go).getHealth()/go.proto.health, 30);
                g.setColor(Color.white);

                // render name above
                g.setFont(new Font("Arial", 0, 12));
                g.setColor(Color.white.brighter());
                g.drawString(go.getId(), go.getX()-go.image.getWidth()/2, go.getY()-go.image.getHeight());
            }

            if (go instanceof Enemy) {
                // renders hp bar for each enemy
                g.setFont(new Font("Arial", 0, 18));
                g.setColor(Color.white.brighter());
                g.setColor(Color.red.darker());
                g.fillRect(go.getX()-20, go.getY()-go.image.getHeight(), 40, 5);
                g.setColor(Color.green.darker());
                g.fillRect(go.getX()-20, go.getY()-go.image.getHeight(), 40*((Enemy) go).getHealth()/go.proto.health, 5);
                g.setColor(Color.white);
            }
        }

        if(endGame || winGame){
            String gameMessage = (endGame) ? "Game Over":"Victory!";
            Font f = new Font("Arial", 0, 60);
            FontMetrics metrics = g.getFontMetrics(f);
            g.setFont(f);
            g.setColor(Color.white.brighter());
            Game ga = Game.getInstance();
            g.drawString(gameMessage, ga.getWidth()/ 2 - metrics.stringWidth(gameMessage)/2, ga.getHeight()/2);
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


    private void checkEndGame(){
        java.util.List<GameObject> relevantObjects = Main.gameObjects.stream().filter(go -> go.getClass() == Player.class).collect(Collectors.toList());
        if(relevantObjects.size() == 0){
            this.endGame = true;
        }
    }

    private void checkWinGame(){
        java.util.List<GameObject> relevantObjects = Main.gameObjects.stream().filter(go -> go.getClass() == Enemy.class).collect(Collectors.toList());
        if(relevantObjects.size() == 0){
            this.winGame = true;
        }
    }

    public String getName() {
        return this.name;
    }
}
