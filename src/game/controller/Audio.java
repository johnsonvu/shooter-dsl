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
            clip.start();

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
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