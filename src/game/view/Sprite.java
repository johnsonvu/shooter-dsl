package game.view;

import game.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static lib.Util.randomInt;

public class Sprite {
    private static final String PLAYER_PATH = "media/Player/";
    private static final String ENEMY_PATH = "media/Enemy/";
    private static final String ITEM_PATH = "media/Item/";
    private static final String PROJECTILE_PATH = "media/Projectile/";
    private static final String BACKGROUND_PATH = "media/Background/";

    private List<Integer> playerSpritIndex;

    private static BufferedImage projectiles;

    public Sprite() {
        playerSpritIndex= new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    }

    public BufferedImage loadImage(Object obj) {
        try {
            String cwd = System.getProperty("user.dir");
            String path = getPath(obj);
            return ImageIO.read(new File(cwd.replaceAll("/src", "") + "/" + path));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    private String getPath(Object obj) {
        if (obj instanceof Game) {
            return BACKGROUND_PATH + randomInt(1, 8) + ".png";
        } else if (obj instanceof Player) {
            int index = randomInt(0, playerSpritIndex.size() - 1);
            int value = playerSpritIndex.get(index);
            playerSpritIndex.remove(index);
            return PLAYER_PATH + value + ".png";
        } else if (obj instanceof Enemy) {
            return ENEMY_PATH + randomInt(7, 13) + ".png";
        } else if (obj instanceof Item) {
            return ITEM_PATH + "Gem" + randomInt(1, 4) + ".gif";
        } else {
            // TODO: handle unique projectile for a player
//            return PROJECTILE_PATH + randomInt(1, 14) + ".png";
            return PROJECTILE_PATH + 1 + ".png";
        }
    }
}
