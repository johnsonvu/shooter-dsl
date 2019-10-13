package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.controller.Handler;
import game.controller.KeyInput;
import ui.Main;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

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

    private Game() {
        name = "ShootingGame";
        width = 1024;
        height = 768;

        sprite = new Sprite();
        handler = new Handler();

        // set bg image to 2x by 2x scaling
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

    // TODO: add health bar for all players
    public void paint(Graphics g) {
        g.drawImage(level, 0,0,null);
        Main.game.getHandler().render(g);
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

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }
}
