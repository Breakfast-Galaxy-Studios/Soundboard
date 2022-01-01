package net.breakfaststudios.util;

import net.breakfaststudios.BreakfastSounds;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

/**
 * General purpose class for things are constantly accessed / referenced.
 */
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
        // Get the jar path
        try {
            jarPath = BreakfastSounds.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error occurred when getting jar path.");
        }
    }

    /**
     * Get a string representation of where the Sounds are stored.
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
     * Get a string representation of the Main Working Directory.
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
    public static void updateSettings(String soundOutput, boolean keyCompatMode, boolean openToTray, boolean darkMode, boolean openOnStartup, int gcTime) {
        File settingsFile = new File(Util.getMainDirectory() + "settings.properties");
        Properties settings = new Properties();
        settings.setProperty("soundOutput", soundOutput);
        settings.setProperty("keyCompatMode", String.valueOf(keyCompatMode));
        settings.setProperty("openToTray", String.valueOf(openToTray));
        settings.setProperty("darkMode", String.valueOf(darkMode));
        settings.setProperty("openOnStartup", String.valueOf(openOnStartup));
        // Potentially allow user to set this in the future. default 300k
        settings.setProperty("gcTime", String.valueOf(gcTime));
        if (!settingsFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
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

    public static void updateSettings(String soundOutput, boolean keyCompatMode, boolean openToTray, boolean darkMode, boolean openOnStartup) {
        updateSettings(soundOutput, keyCompatMode, openToTray, darkMode, openOnStartup, 300000);
    }

    /**
     * Gets the settings file from the main app dir.
     *
     * @return Properties file if it exists, else returns null.
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
            // builder.append(NativeKeyEvent.getKeyText(toAdd)).append(" + ");
            builder.append(Converter.getKeyText(toAdd)).append(" + ");
        }
    }

    /**
     * General purpose method for other windows, as they are added.
     * @param p ArrayList of panels so that you can set the style of all child elements.
     * @param backgroundColor Color of the background
     * @param textColor Color of the text
     */
    public static void setTheme(ArrayList<Panel> p, Color backgroundColor, Color textColor) {
        // All components
        p.forEach(panel -> {
            for (Component c : panel.getComponents()) {
                JComponent jc = (JComponent) c;
                if (jc instanceof JButton) {
                    jc.setOpaque(true);
                    jc.setBackground(backgroundColor);
                } else {
                    jc.setBackground(backgroundColor);
                    jc.setForeground(textColor);
                    jc.setOpaque(true);
                }
            }
            panel.setBackground(backgroundColor);
        });
    }

    /**
     * Changes open to startup settings.
     *
     * @param bool True if you want it to open on startup, else false.
     */
    public static void openOnStartup(boolean bool) {
        if (os.contains("win")) {
            startupWindows(bool);
        } else if (os.equals("mac")) {
            startupMac(bool);
        } else if (os.contains("nux")) {
            startupLinux(bool);
        } else {
            JOptionPane.showMessageDialog(null, "This application currently doesn't support openOnStartup for this operating system.\nIf you think this is an error, or would like to request this feature be added for your OS, please create an issue on the GitHub Repo.");
        }
    }

    private static void startupWindows(boolean bool) {
        Path winStartupBatch = Paths.get(getMainDirectory() + "soundboard.bat");
        Path winStartupScript = Paths.get(System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\soundboard.vbs");
        String script = "Set WshShell = CreateObject(\"WScript.Shell\") \n" + "WshShell.Run chr(34) & \"" + winStartupBatch + "\" & Chr(34), 0\n" + "Set WshShell = Nothing";
        if (bool) {
            try {
                String[] newPath;
                if (jarPath != null) {
                    newPath = jarPath.split("/");
                } else {
                    throw new Exception();
                }

                StringBuilder operatingPath = new StringBuilder(String.join("/", newPath));
                operatingPath.deleteCharAt(0);
                String fileContents = "java -jar \"" + operatingPath + "\"";

                if (!Files.exists(winStartupBatch)) Files.createFile(winStartupBatch);
                if (Files.exists(winStartupBatch)) {
                    Files.writeString(winStartupBatch, fileContents);
                }

                if (!Files.exists(winStartupScript)) Files.createFile(winStartupScript);
                if (Files.exists(winStartupScript)) {
                    Files.writeString(winStartupScript, script);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.deleteIfExists(winStartupBatch);
                Files.deleteIfExists(winStartupScript);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to remove from startup folder.");
                ex.printStackTrace();
            }
        }
    }

    private static void startupMac(boolean bool) {
        // TODO implement this
    }

    private static void startupLinux(boolean bool) {
        // TODO: implement linux support here
        // TODO: TEST LINUX SUPPORT
        InputStream is = Util.class.getClassLoader().getResourceAsStream("soundboard.service");
        File systemd = new File("/etc/systemd/system/");
        File file = new File("soundboard.service");
        if (bool) {
            try {
                boolean dirCreated;

                // Make sure the dirs exist and create the file
                if (!file.exists()) {
                    dirCreated = systemd.mkdirs();
                    Files.createFile(Path.of("/etc/systemd/system/soundboard.service"));
                } else {
                    return;
                }

                // Throw exception if startup dir couldn't be created.
                if (!dirCreated || is == null) throw new IOException("Startup Directory could not be created.");

                // Create the contents of the file.
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(fileWriter);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;

                System.out.println("Test1");

                // Replace unique things in the file
                while ((line = reader.readLine()) != null) {
                    // Replace jar path in startup script to exact path
                    if (line.equals("ExecStart=/usr/bin/java -jar %JAR_PATH%")) {
                        line = line.replace("%JAR_PATH%", BreakfastSounds.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "soundboard.jar");
                    }
                    // Replace user in service to actual username
                    if (line.equals("User=%USER%")) {
                        line = line.replace("%USER%", System.getProperty("user.name"));
                    }
                    out.append(line);
                    out.newLine();
                }

                // Close the streams
                reader.close();
                out.close();


                /*
                 * Execute necessary commands
                 */
                Runtime runtime = Runtime.getRuntime();
                // Reload the daemon
                String[] reloadDaemon = {"sudo", "systemctl", "daemon-reload"};
                runtime.exec(reloadDaemon);

                // Enable the service
                String[] enableService = {"sudo", "systemctl", "enable", "soundboard"};
                runtime.exec(enableService);
                System.out.println("Test2");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            // If file exist, disable the service
            try {
                if (Files.exists(file.toPath())) {
                    String[] disableService = {"sudo", "systemctl", "disable", "soundboard"};
                    Runtime.getRuntime().exec(disableService);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, """
                        Failed to disable the service.
                        You can disable the service yourself by running the following command:
                        sudo systemctl disable soundboard
                        """);
            }
        }
    }
}