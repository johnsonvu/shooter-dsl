package game.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Handler;

public class KeyInput extends KeyAdapter {

    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // TODO: Add key press logic

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // TODO: Add key release logic

    }
}
