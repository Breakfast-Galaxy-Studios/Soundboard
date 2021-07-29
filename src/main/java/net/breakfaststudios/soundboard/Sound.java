package net.breakfaststudios.soundboard;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Integer[] keys;
    private String name;
    private String path;
    private float volume;
    private long length;

    public Sound(String soundName, String path, Integer[] keys, float volume) {
        this.name = soundName;
        this.path = path;
        this.volume = volume;
        this.keys = keys;

        try {
            File file = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            double durationInSeconds = ((double) frames) / format.getFrameRate();
            this.length = (long)(durationInSeconds * 1000) + 250;

        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, "You prolly won't see this, but literally you cannot play that file. (" + path + " )");
            System.exit(90);
        }
    }

    public long getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public Integer[] getKeys() {
        return keys;
    }

    public void setKeys(Integer[] newKeys) {
        this.keys = newKeys;
    }
}
