package game.model;

public class Item {
    private String itemId;
    private int number;
    // add coordinate?

    public Item(String id){
        itemId = id;
    }
    public Item(String id, int number) {
        itemId = id;
        this.number = number;
    }
}
