package game.controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {

    private File file;
    private Clip clip;
    private Boolean loop;

    public Audio(String path) {
        this.file = new File(path);
        loop = false;
    }

    public void play() {
        try {
            clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(file));
            if(loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            // reduce audio by 40 decibals
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-40.0f); // Reduce volume by 10 decibels.

            clip.start();

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

    }

    public void setLoop(Boolean set) {
        loop = set;
    }

    public void stop() {
        clip.stop();
    }
}