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
    public static void createNewSound(String name, String filePath, String keybind, float volume) {
        String userConfigDir = Util.getSoundDirectory();
        String soundPath = userConfigDir + name + ".properties";
        try (OutputStream output = new FileOutputStream(soundPath)) {
            createNewSoundProp(userConfigDir, name, filePath, keybind, volume, output);
        } catch (Exception ignore) {
            Path path = Paths.get(userConfigDir);
            File soundDir = new File(String.valueOf(path));

            if (soundDir.mkdir()) {
                try (OutputStream output = new FileOutputStream(soundPath)) {
                    createNewSoundProp(userConfigDir, name, filePath, keybind, volume, output);

                    ArrayList<Integer> keys = new ArrayList<>();

                    for (String key : keybind.split("_")) {
                        keys.add(Integer.valueOf(key));
                    }

                    Integer[] arr = new Integer[keys.size()];
                    for (int i = 0; i < keys.size(); i++)
                        arr[i] = keys.get(i);

                    BreakfastSounds.getSoundBoard().addSound(new Sound(name, filePath, arr, volume));

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fatal exception when adding Sound. Error is as follows:\n" + ex + "\nPlease report this error to the Github Repo.");
                    System.exit(90);
                }
            }
        }
    }

    private static void createNewSoundProp(String userConfigDir, String name, String filePath, String keybind, float volume, OutputStream output) {
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
                File soundDir = new File(String.valueOf(path));
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
            File actualFile = new File(filePath + fileName);
            return actualFile.delete();

        } catch (Exception ignored) {
            return false;
        }

    }
}