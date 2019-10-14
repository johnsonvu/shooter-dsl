package lib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.lang.Math.abs;

public class Util {
    public static int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // Source:  https://stackoverflow.com/questions/44086310/how-to-rotate-a-buffered-image-without-cropping-it-is-there-any-way-to-rotate-a
    public static BufferedImage rotate(BufferedImage originalImage, double degree) {
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        double toRad = Math.toRadians(degree);
        int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
        int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

        BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, originalImage.getType());
        Graphics2D g = rotatedImage.createGraphics();
        g.translate(wPrime/2, hPrime/2);
        g.rotate(toRad);
        g.translate(-w/2, -h/2);
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();  // release used resources before g is garbage-collected
        return rotatedImage;
    }
}
