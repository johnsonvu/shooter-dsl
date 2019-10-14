package game.model;

import game.view.Game;
import evaluate.protoypes.ItemProto;
import ui.Main;

import java.awt.*;

public class Item extends GameObject {
    public Item(ItemProto proto, String name) {
        super(proto, name);
        image = Game.sprite.loadImage(this);
    }

    @Override
    public void tick() {
        collision();
    }

    private void collision() {
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            GameObject obj = Main.gameObjects.get(i);
            if (obj instanceof Player) {
                if (this.getBounds().intersects(obj.getBounds())) {
                    if(obj.health < obj.proto.health) {
                        obj.health += this.health;
                    }
                    Main.gameObjects.remove(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }
}
