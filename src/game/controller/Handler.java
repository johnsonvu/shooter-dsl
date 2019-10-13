package game.controller;

import game.model.GameObject;
import lib.KEYINPUTTYPE;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Handler {

    // TODO: change to reference main.objectlist
    public LinkedList<GameObject> objects = new LinkedList<GameObject>();

    // Object states for moving: up, down, left, right and shooting
    public HashMap<GameObject, HashMap<KEYINPUTTYPE, Boolean>> objectStates = new HashMap<>();

    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.render(g);
        }
    }

    public boolean isUp(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KEYINPUTTYPE.UP)) {
            return objectStates.get(obj).get(KEYINPUTTYPE.UP);
        }
        // no state, default to false
        return false;
    }

    public void setUp(GameObject obj, boolean up) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KEYINPUTTYPE, Boolean>());
        }
        objectStates.get(obj).put(KEYINPUTTYPE.UP, up);
    }

    public boolean isDown(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KEYINPUTTYPE.DOWN)) {
            return objectStates.get(obj).get(KEYINPUTTYPE.DOWN);
        }
        // no state, default to false
        return false;
    }

    public void setDown(GameObject obj, boolean down) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KEYINPUTTYPE, Boolean>());
        }
        objectStates.get(obj).put(KEYINPUTTYPE.DOWN, down);

    }

    public boolean isLeft(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KEYINPUTTYPE.LEFT)) {
            return objectStates.get(obj).get(KEYINPUTTYPE.LEFT);
        }
        // no state, default to false
        return false;
    }

    public void setLeft(GameObject obj, boolean left) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KEYINPUTTYPE, Boolean>());
        }
        objectStates.get(obj).put(KEYINPUTTYPE.LEFT, left);
    }

    public boolean isRight(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KEYINPUTTYPE.RIGHT)) {
            return objectStates.get(obj).get(KEYINPUTTYPE.RIGHT);
        }
        // no state, default to false
        return false;
    }

    public void setRight(GameObject obj, boolean right) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KEYINPUTTYPE, Boolean>());
        }
        objectStates.get(obj).put(KEYINPUTTYPE.RIGHT, right);
    }

    public boolean isShoot(GameObject obj) {
        // object has a state stored
        if(objectStates.containsKey(obj) && objectStates.get(obj).containsKey(KEYINPUTTYPE.SHOOT)) {
            return objectStates.get(obj).get(KEYINPUTTYPE.SHOOT);
        }
        // no state, default to false
        return false;
    }

    public void setShoot(GameObject obj, boolean shoot) {
        // object has no state
        if(!objectStates.containsKey(obj)) {
            objectStates.put(obj, new HashMap<KEYINPUTTYPE, Boolean>());
        }
        objectStates.get(obj).put(KEYINPUTTYPE.SHOOT, shoot);
    }
}
