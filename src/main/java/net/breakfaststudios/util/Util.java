package net.breakfaststudios.util;

import net.breakfaststudios.BreakfastSounds;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * @param openOnStartup Bool representing if open on startup is on or off.
     */
    public static void updateSettings(String soundOutput, boolean keyCompatMode, boolean openToTray, boolean darkMode, boolean openOnStartup) {
        File settingsFile = new File(Util.getMainDirectory() + "settings.properties");
        Properties settings = new Properties();
        settings.setProperty("soundOutput", soundOutput);
        settings.setProperty("keyCompatMode", String.valueOf(keyCompatMode));
        settings.setProperty("openToTray", String.valueOf(openToTray));
        settings.setProperty("darkMode", String.valueOf(darkMode));
        settings.setProperty("openOnStartup", String.valueOf(openOnStartup));
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

    /**
     * Changes open to startup settings.
     * @param bool True if you want it to open on startup, else false.
     */
    public static void openOnStartup(boolean bool){
        // todo make shortcut maybe, this way works fine tho
        Path winStartupBatch = Paths.get(Util.getMainDirectory() + "soundboard.bat");
        Path winStartupScript = Paths.get(System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\soundboard.vbs");
        String script = "Set WshShell = CreateObject(\"WScript.Shell\") \n" + "WshShell.Run chr(34) & \"" + winStartupBatch + "\" & Chr(34), 0\n" + "Set WshShell = Nothing";
        if (os.contains("win")){
            if (bool){
                try{
                    String[] newPath;
                    if (jarPath != null){
                        newPath = jarPath.split("/");
                    } else {throw new Exception();}

                    StringBuilder operatingPath = new StringBuilder(String.join("/", newPath));
                    operatingPath.deleteCharAt(0);
                    String fileContents = "java -jar \"" + operatingPath + "\"";

                    if (!Files.exists(winStartupBatch)) Files.createFile(winStartupBatch);
                    if (Files.exists(winStartupBatch)){
                        Files.writeString(winStartupBatch, fileContents);
                    }

                    if (!Files.exists(winStartupScript)) Files.createFile(winStartupScript);
                    if (Files.exists(winStartupScript)){
                        Files.writeString(winStartupScript, script);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                try{
                    Files.deleteIfExists(winStartupBatch);
                    Files.deleteIfExists(winStartupScript);
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(null, "Failed to remove from startup folder.");
                    ex.printStackTrace();
                }
            }
        } else if (os.equals("mac")){
            // TODO implement macOS support here
        } else if (os.contains("nux")){
            // TODO implement linux support here
        } else {
            JOptionPane.showMessageDialog(null, "This application currently doesn't support openOnStartup for this operating system.\nIf you think this is an error, or would like to request this feature be added for your OS, please create an issue on the GitHub Repo.");
        }
    }
}
