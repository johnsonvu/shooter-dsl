package game.model;

import java.awt.*;

public class Item extends GameObject {
    private String itemId;
    private int number;
    // add coordinate?

    public Item(int x, int y, String id) {
        super(x, y, id);
        itemId = id;
        this.number = number;
    }
    public Item(String id){
        this(1,1,id);
    }
}
