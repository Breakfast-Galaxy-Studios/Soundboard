package net.breakfaststudios.soundboard;

import net.breakfaststudios.BreakfastSounds;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * This class basically is just for playing sounds on threads
 */
public class SoundThread extends Thread {

    private final String path;
    private final float volume;
    private final long clipLength;

    /**
     * The normal sound thread constructor
     *
     * @param path       Path to the sound
     * @param volume     Volume of the sound
     * @param clipLength Length of the sound
     */
    public SoundThread(String path, float volume, long clipLength) {
        this.path = path;
        this.volume = volume;
        this.clipLength = clipLength;
    }

    /**
     * Gets the speakers as a Mixer.info
     *
     * @return Mixer.info speakers
     */
    private static Mixer.Info getSpeakers() {
        Mixer.Info speakers = null;
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

        for (Mixer.Info mi : mixerInfo) {
            if (mi.getName().equals(BreakfastSounds.SELECTED_AUDIO_DEVICE)) {
                speakers = mi;
                break;
            }
        }

        return speakers;
    }

    /**
     * Tries to play a sound with the information provided
     */
    @Override
    public void run() {
        AudioInputStream inputStream = null;
        AudioFormat reformatFormat = new AudioFormat(44100.0F, 16, 2, true, false);
        try {
            inputStream = AudioSystem.getAudioInputStream(new File(path));
            inputStream = AudioSystem.getAudioInputStream(reformatFormat, inputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couldn't play sound. Make sure the sound file exist. Error is as follows:" + e);
            System.exit(56);
        }
        try {
            Clip clip = AudioSystem.getClip(getSpeakers());
            // Try opening the sound file, reading it to stream
            clip.open(inputStream);

            // Set volume of the clip, using percents
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
            float gain = (range * volume) + volumeControl.getMinimum();
            volumeControl.setValue(gain);

            // Start clip, wait for it to play, then close it so java can garbage collect it.
            clip.start();
            sleep(clipLength);
            clip.drain();
            clip.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
