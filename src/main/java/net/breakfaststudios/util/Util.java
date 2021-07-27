package net.breakfaststudios.util;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Util {
    public static final String os = System.getProperty("os.name").toLowerCase();

    public static String getSoundDirectory() {
        if (os.contains("win"))
            return System.getenv("APPDATA") + "\\BGS-Soundboard\\sounds\\";
        else if (os.contains("mac") || os.contains("nux"))
            return System.getProperty("user.home") + "/BGS-Soundboard/sounds/";
        else
            return System.getProperty("user.dir") + "/BGS-Soundboard/sounds/";
    }

    public static String getMainDirectory() {
        if (os.contains("win"))
            return System.getenv("APPDATA") + "\\BGS-Soundboard\\";
        else if (os.contains("mac") || os.contains("nux"))
            return System.getProperty("user.home") + "/BGS-Soundboard/";
        else
            return System.getProperty("user.dir") + "/BGS-Soundboard/";
    }

    public static void updateSettings(String soundOutput, boolean keyCompatMode, boolean openToTray) {
        File settingsFile = new File(Util.getMainDirectory() + "settings.properties");
        Properties settings = new Properties();
        settings.setProperty("soundOutput", soundOutput);
        settings.setProperty("keyCompatMode", String.valueOf(keyCompatMode));
        settings.setProperty("openToTray", String.valueOf(openToTray));
        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
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

    public static Properties getSettingsFile() {
        try {
            FileInputStream file = new FileInputStream(Util.getMainDirectory() + "settings.properties");
            Properties prop = new Properties();
            prop.load(file);
            file.close();
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\n\nError occurs on first setup. If this isn't first setup there is a problem.\n\n");
            return null;
        }
    }

    public static Integer[] intListToArray(ArrayList<Integer> list) {
        Integer[] intArray = new Integer[list.size()];

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = list.get(i);
        }

        return intArray;
    }

    public static void parseRawCodeText(String rawText, ArrayList<Integer> keys, StringBuilder builder) {
        for (String key : rawText.split("_")) {
            if (key.equalsIgnoreCase("none"))
                break;

            int toAdd = Integer.parseInt(key);
            keys.add(toAdd);
            builder.append(NativeKeyEvent.getKeyText(toAdd)).append(" + ");
        }
    }
}
