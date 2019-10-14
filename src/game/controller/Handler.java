package game.controller;

import game.model.GameObject;
import lib.enums.KeyInputType;
import ui.Main;

import java.awt.*;
import java.util.HashMap;

public class Handler {
    // Object states for moving: up, down, left, right and shooting
    public HashMap<GameObject, HashMap<KeyInputType, Boolean>> objectStates = new HashMap<>();

    public void tick() {
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            GameObject tempObject = Main.gameObjects.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            GameObject tempObject = Main.gameObjects.get(i);
            tempObject.render(g);
        }
    }

    public boolean isUp(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KeyInputType.UP)) {
            return objectStates.get(obj).get(KeyInputType.UP);
        }
        // no state, default to false
        return false;
    }

    public void setUp(GameObject obj, boolean up) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KeyInputType, Boolean>());
        }
        objectStates.get(obj).put(KeyInputType.UP, up);
    }

    public boolean isDown(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KeyInputType.DOWN)) {
            return objectStates.get(obj).get(KeyInputType.DOWN);
        }
        // no state, default to false
        return false;
    }

    public void setDown(GameObject obj, boolean down) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KeyInputType, Boolean>());
        }
        objectStates.get(obj).put(KeyInputType.DOWN, down);

    }

    public boolean isLeft(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KeyInputType.LEFT)) {
            return objectStates.get(obj).get(KeyInputType.LEFT);
        }
        // no state, default to false
        return false;
    }

    public void setLeft(GameObject obj, boolean left) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KeyInputType, Boolean>());
        }
        objectStates.get(obj).put(KeyInputType.LEFT, left);
    }

    public boolean isRight(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KeyInputType.RIGHT)) {
            return objectStates.get(obj).get(KeyInputType.RIGHT);
        }
        // no state, default to false
        return false;
    }

    public void setRight(GameObject obj, boolean right) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KeyInputType, Boolean>());
        }
        objectStates.get(obj).put(KeyInputType.RIGHT, right);
    }

    public boolean isShoot(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KeyInputType.SHOOT)) {
            return objectStates.get(obj).get(KeyInputType.SHOOT);
        }
        // no state, default to false
        return false;
    }

    public void setShoot(GameObject obj, boolean shoot) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KeyInputType, Boolean>());
        }
        objectStates.get(obj).put(KeyInputType.SHOOT, shoot);
    }
}
