package game.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.logging.Handler;

import game.model.GameObject;
import game.model.Player;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private HashMap<GameObject, List<Integer>> keyMap;
    private Queue<List<Integer>> playerKeyEventsQueue;

    public KeyInput(Handler handler) {
        // queue of keyevents {up, left, down, right}
        playerKeyEventsQueue = new LinkedList<>();
        playerKeyEventsQueue.add(new LinkedList<Integer>(Arrays.asList(KeyEvent.VK_W,KeyEvent.VK_A,KeyEvent.VK_S,KeyEvent.VK_D, KeyEvent.VK_C)));
        playerKeyEventsQueue.add(new LinkedList<Integer>(Arrays.asList(KeyEvent.VK_Y,KeyEvent.VK_G,KeyEvent.VK_H,KeyEvent.VK_J, KeyEvent.VK_M)));
        playerKeyEventsQueue.add(new LinkedList<Integer>(Arrays.asList(KeyEvent.VK_P,KeyEvent.VK_L,KeyEvent.VK_SEMICOLON,KeyEvent.VK_QUOTE, KeyEvent.VK_SHIFT)));

        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObj = handler.objects.get(i);

            // TODO: Add support for multiple 
            if (tempObj instanceof Player) {
                List<Integer> keyEvents;

                // retrieve player key set
                if(keyMap.containsKey(tempObj)) {
                    keyEvents = keyMap.get(tempObj);
                } else {
                    // initially assign a player object their key set, first time
                    keyEvents = playerKeyEventsQueue.poll();
                    keyMap.put(tempObj, keyEvents);
                }

				if (key == keyEvents.get(0)) {
					handler.setUp(tempObj, true);
				}
				if (key == keyEvents.get(1)) {
					handler.setLeft(tempObj, true);
				}
                if (key == keyEvents.get(2)) {
                    handler.setDown(tempObj, true);
                }
                if (key == keyEvents.get(3)) {
                    handler.setRight(tempObj, true);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObj = handler.objects.get(i);

            // TODO: Add support for multiple
            if (tempObj instanceof Player) {
                List<Integer> keyEvents;

                // retrieve player key set
                if(keyMap.containsKey(tempObj)) {
                    keyEvents = keyMap.get(tempObj);
                } else {
                    // initially assign a player object their key set, first time
                    keyEvents = playerKeyEventsQueue.poll();
                    keyMap.put(tempObj, keyEvents);
                }

                if (key == keyEvents.get(0)) {
                    handler.setUp(tempObj, false);
                }
                if (key == keyEvents.get(1)) {
                    handler.setLeft(tempObj, false);
                }
                if (key == keyEvents.get(2)) {
                    handler.setDown(tempObj, false);
                }
                if (key == keyEvents.get(3)) {
                    handler.setRight(tempObj, false);
                }
            }
        }

    }
}
