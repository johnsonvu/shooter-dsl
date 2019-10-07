package game;

import ast.Identifier;

public class Item {
    private Identifier itemId;
    private Number number;
    // add coordinate?

    public Item(Identifier id, Number number) {
        itemId = id;
        this.number = number;
    }
}
