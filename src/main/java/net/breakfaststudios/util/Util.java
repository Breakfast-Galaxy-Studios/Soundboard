package net.breakfaststudios.util;

import net.breakfaststudios.BreakfastSounds;
import org.jnativehook.keyboard.NativeKeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

public class Util {
    /**
     * Returns the name of the OS.
     */
    public static final String os = System.getProperty("os.name").toLowerCase();
    /**
     * Path of the current location of the jar file.
     */
    public static String jarPath = null;
    static {
        try {
            jarPath = BreakfastSounds.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error occurred when getting jar path.");
        }
    }

    /**
     * @return String representing the dir that the sound-config files are stored.
     */
    public static String getSoundDirectory() {
        if (os.contains("win"))
            return System.getenv("APPDATA") + "\\BGS-Soundboard\\sounds\\";
        else if (os.contains("mac") || os.contains("nux"))
            return System.getProperty("user.home") + "/BGS-Soundboard/sounds/";
        else
            return System.getProperty("user.dir") + "/BGS-Soundboard/sounds/";
    }

    /**
     * @return String of the main app folder.
     */
    public static String getMainDirectory() {
        if (os.contains("win"))
            return System.getenv("APPDATA") + "\\BGS-Soundboard\\";
        else if (os.contains("mac") || os.contains("nux"))
            return System.getProperty("user.home") + "/BGS-Soundboard/";
        else
            return System.getProperty("user.dir") + "/BGS-Soundboard/";
    }

    /**
     * Updates the settings file.
     *
     * @param soundOutput   String of the chosen sound output device.
     * @param keyCompatMode Bool representing if keybind compatibility mode is on or off.
     * @param openToTray    Bool representing if open to tray on startup is on or off.
     * @param darkMode      Bool representing if dark mode is on or off.
     */
    public static void updateSettings(String soundOutput, boolean keyCompatMode, boolean openToTray, boolean darkMode) {
        File settingsFile = new File(Util.getMainDirectory() + "settings.properties");
        Properties settings = new Properties();
        settings.setProperty("soundOutput", soundOutput);
        settings.setProperty("keyCompatMode", String.valueOf(keyCompatMode));
        settings.setProperty("openToTray", String.valueOf(openToTray));
        settings.setProperty("darkMode", String.valueOf(darkMode));
        if (!settingsFile.exists()) {
            try {
                boolean a = settingsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream output = new FileOutputStream(settingsFile.getPath());
            settings.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the settings file from the main app dir.
     *
     * @return Properties file if it exist, else returns null.
     */
    public static Properties getSettingsFile() {
        try {
            FileInputStream file = new FileInputStream(Util.getMainDirectory() + "settings.properties");
            Properties prop = new Properties();
            prop.load(file);
            file.close();
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\n\nError tends to occur on first startup.\n\n");
            return null;
        }
    }

    /**
     * Turns arraylist of integers into an Integer[].
     */
    public static Integer[] intListToArray(ArrayList<Integer> list) {
        Integer[] intArray = new Integer[list.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = list.get(i);
        }

        return intArray;
    }

    /**
     * Turns the keycode into the string representation of the key.
     */
    public static void parseRawCodeText(String rawText, ArrayList<Integer> keys, StringBuilder builder) {
        for (String key : rawText.split("_")) {
            if (key.equalsIgnoreCase("none")) break;

            int toAdd = Integer.parseInt(key);
            keys.add(toAdd);
            builder.append(NativeKeyEvent.getKeyText(toAdd)).append(" + ");
        }
    }
}
