package game.controller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Audio {

    private File file;
    private Clip clip;

    public Audio(String path) {
        this.file = new File(path);
    }

    public void play() {
        try {
            clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void stop() {
        clip.stop();
    }
}