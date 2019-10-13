package game.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import game.model.GameObject;
import game.model.Player;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private HashMap<GameObject, List<Integer>> keyMap;
    private Queue<List<Integer>> playerKeyEventsQueue;

    public KeyInput(Handler handler) {
        // queue of keyevents {up, left, down, right}
        keyMap = new HashMap<>();
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

                // set object state key event to true to trigger movements/shoot action
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
                if (key == keyEvents.get(4)) {
                    handler.setShoot(tempObj, true);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObj = handler.objects.get(i);

            if (tempObj instanceof Player) {
                List<Integer> keyEvents;

                // retrieve player key set
                if(keyMap.containsKey(tempObj)) {
                    keyEvents = keyMap.get(tempObj);
                } else {
                    // initially assign a player object unique key set
                    keyEvents = playerKeyEventsQueue.poll();
                    keyMap.put(tempObj, keyEvents);
                }

                // set object state key event to false to stop movements/shoot action
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
                if (key == keyEvents.get(4)) {
                    handler.setShoot(tempObj, false);
                }
            }
        }

    }
}
