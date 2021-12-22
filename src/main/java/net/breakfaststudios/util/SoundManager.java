package net.breakfaststudios.util;

import net.breakfaststudios.BreakfastSounds;
import net.breakfaststudios.soundboard.Sound;
import net.breakfaststudios.soundboard.SoundBoard;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

public class SoundManager {

    /**
     * Creates a new sound config file.
     *
     * @param name     Name of the sound.
     * @param filePath Path to the audio file.
     * @param keybind  Key codes for the keybind.
     * @param volume   Volume level between 0-1.
     */
    public static void createNewSound(String name, String filePath, String keybind, float volume) {
        String userConfigDir = Util.getSoundDirectory();
        String soundPath = userConfigDir + name + ".properties";
        try (OutputStream output = new FileOutputStream(soundPath)) {
            createNewSoundFile(userConfigDir, name, filePath, keybind, volume, output);
        } catch (Exception ignore) {
            Path path = Paths.get(userConfigDir);
            File soundDir = new File(String.valueOf(path));

            if (soundDir.mkdir()) {
                try (OutputStream output = new FileOutputStream(soundPath)) {
                    createNewSoundFile(userConfigDir, name, filePath, keybind, volume, output);

                    ArrayList<Integer> keys = new ArrayList<>();

                    for (String key : keybind.split("_")) {
                        keys.add(Integer.valueOf(key));
                    }

                    Integer[] arr = Util.intListToArray(keys);

                    BreakfastSounds.getSoundBoard().addSound(new Sound(name, filePath, arr, volume));

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fatal exception when adding Sound. Error is as follows:\n" + ex + "\nPlease report this error to the Github Repo.");
                    System.exit(90);
                }
            }
        }
    }

    /**
     * Creates a new sound file.
     *
     * @param userConfigDir Directory for the save file.
     * @param name          Name of the sound.
     * @param filePath      Path to the audio file.
     * @param keybind       Key codes for the keybind.
     * @param volume        Volume level between 0-1.
     * @param output        Output stream for the file.
     */
    private static void createNewSoundFile(String userConfigDir, String name, String filePath, String keybind, float volume, OutputStream output) {
        Path path = Paths.get(userConfigDir);
        Properties prop = new Properties();
        prop.setProperty("name", name);
        prop.setProperty("filepath", filePath);
        prop.setProperty("keybind", keybind);
        prop.setProperty("volume", String.valueOf(volume / 100));

        if (Files.exists(path)) {
            try {
                prop.store(output, null);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to add sound. If this keeps occurring, please report it to the Github Repo.");
                e.printStackTrace();
            }
        } else {
            try {
                File soundDir = new File(path.toString());
                if (soundDir.mkdir()) {
                    prop.store(output, null);
                } else {
                    JOptionPane.showMessageDialog(null, "Fatal error when saving sound. If this keeps occurring, please report it to the Github Repo.");
                    System.exit(88);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Fatal exception when creating Dir. Error is as follows:\n" + ex + "\nPlease report this error to the Github Repo.");
                System.exit(33);
            }
        }
    }

    /**
     * Deletes the specified sound file, and removes the sound from the cache.
     *
     * @param filePath Path of the file.
     * @param fileName Name of the file.
     * @return True if file is deleted, false otherwise.
     */
    public static boolean removeSound(String filePath, String fileName) {
        try {
            FileInputStream file = new FileInputStream(Util.getSoundDirectory() + fileName);

            /*
             * Remove Sound from SoundBoard cache.
             */
            Properties prop = new Properties();
            prop.load(file);
            SoundBoard sb = BreakfastSounds.getSoundBoard();
            sb.removeSound(sb.getSound(prop.getProperty("name")));

            /*
             * Actually Deletes the file.
             */
            file.close();
            return Files.deleteIfExists(Path.of(filePath + fileName));

        } catch (Exception ignored) {
            return false;
        }
    }


    public static Properties getSoundConfig(String soundName) {
        try {
            FileInputStream file = new FileInputStream(Util.getSoundDirectory() + soundName + ".properties");
            Properties prop = new Properties();
            prop.load(file);
            file.close();
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}