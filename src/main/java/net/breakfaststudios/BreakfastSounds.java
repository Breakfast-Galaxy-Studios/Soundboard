package net.breakfaststudios;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import net.breakfaststudios.soundboard.SoundBoard;
import net.breakfaststudios.soundboard.interception.InterceptionMain;
import net.breakfaststudios.soundboard.listeners.GlobalKeyListener;
import net.breakfaststudios.ui.UI;
import net.breakfaststudios.util.Updater;
import net.breakfaststudios.util.Util;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BreakfastSounds {
    /*
     * TODO For every release make sure this is changed. It should correspond to the github tag for the release.
     */
    public static final String currentVersion = "v2.2";
    public static String SELECTED_AUDIO_DEVICE = "Primary Sound Driver";
    public static JFrame dialogParent = new JFrame();
    private static SoundBoard soundBoard;
    private static NativeKeyListener listener;

    /**
     * Creates the UI, and initializes all listeners.
     */
    public BreakfastSounds() {
        new UI().build();
    }

    /**
     * Main method for the program.
     *
     * @param args Accepts no command line arguments.
     */
    //TODO Add audio.properties
    public static void main(String[] args) {
        dialogParent.setAlwaysOnTop(true);
        soundBoard = new SoundBoard();
        Properties settings;

        /*
         * Make sure AutoUpdater is deleted if it exists.
         */
        if (Files.exists(Path.of(Util.getMainDirectory() + "autoupdater.jar"))) {
            try {
                Files.delete(Path.of(Util.getMainDirectory() + "autoupdater.jar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // UI Scaling will be slightly messed up outside a Windows OS.
        // This is done here to correctly style any error messages displayed to users.
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore) {
            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (Exception ignored) {
            }
        }
        // TODO REMOVE THIS
        // new InterceptionUI(null, true).setVisible(true);

        // Check for updates
        Updater.runAutoUpdater();

        //Validate Settings
        settings = validateSettings();

        // Create and display the UI
        makeAppDir();
        EventQueue.invokeLater(BreakfastSounds::new);

        // Register type of listener
        initKeyListener();

        // Load some stuff from settings
        if (settings != null) {
            SELECTED_AUDIO_DEVICE = settings.getProperty("soundOutput");
            if (settings.getProperty("gcTime") != null) {
                startAutoCollection(Integer.parseInt(settings.getProperty("gcTime")));
            } else {
                startAutoCollection(300000);
            }
        } else {
            startAutoCollection(300000);
        }

        // Register key listeners
        listener = new GlobalKeyListener();
        GlobalScreen.addNativeKeyListener(listener);
    }

    /**
     * Validate the settings to check that they are all set and not null.
     *
     * @return New setting value that can be null. It turns out null if Util.getSettingsFile() returns null.
     */
    private static Properties validateSettings() {
        Properties settings;
        if (!Files.exists(Path.of(Util.getMainDirectory() + "settings.properties"))) {
            String soundOutput = "Primary Sound Driver";
            Util.updateSettings(soundOutput, false, false, false, false, 300000);
            settings = Util.getSettingsFile();
        } else {
            settings = Util.getSettingsFile();
            Properties validateSettings = new Properties();

            assert settings != null;
            for (Object key : settings.keySet()) {
                String keyAsString = (String) key;
                if (settings.getProperty(keyAsString) == null) {
                    if (key.equals("soundOutput")) {
                        validateSettings.setProperty(keyAsString, "Primary Sound Driver");
                        continue;
                    }
                    if (key.equals("gcTime")) {
                        validateSettings.setProperty(keyAsString, "300000");
                        continue;
                    }
                    validateSettings.setProperty(keyAsString, "false");
                } else {
                    if (key.equals("soundOutput")) {
                        validateSettings.setProperty(keyAsString, settings.getProperty("soundOutput"));
                        continue;
                    }
                    if (key.equals("gcTime")) {
                        validateSettings.setProperty(keyAsString, settings.getProperty("gcTime"));
                        continue;
                    }
                    validateSettings.setProperty(keyAsString, settings.getProperty((String) key));
                }
            }

            if (!validateSettings.equals(settings)) {
                Util.updateSettings(
                        validateSettings.getProperty("soundOutput"),
                        Boolean.parseBoolean(validateSettings.getProperty("keyCompatMode")),
                        Boolean.parseBoolean(validateSettings.getProperty("openToTray")),
                        Boolean.parseBoolean(validateSettings.getProperty("darkMode")),
                        Boolean.parseBoolean(validateSettings.getProperty("openOnStartup")),
                        Integer.parseInt(validateSettings.getProperty("gcTime"))
                );
            }
        }
        return settings;
    }

    /**
     * Starts the auto garbage collection thread
     *
     * @param time Time between each garbage collection cycle
     */
    private static void startAutoCollection(int time) {
        new Thread(() -> {
            while (true) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Run garbage collection
                // Since logger is already imported for something below
                com.github.malthelegend104.Logger.warn("RUNNING COLLECTION");
                Runtime.getRuntime().gc();
            }
        }).start();
    }

    /**
     * Initializes the type of keylistener to be used.
     */
    private static void initKeyListener() {
        if (Files.exists(Path.of(InterceptionMain.interceptionSettingsFilePath)) && InterceptionMain.getInterceptionSettings().getProperty("interception").equals("true")) {
            InterceptionMain.initKeyboardListener();
        } else {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());
                System.exit(53);
            }
        }
    }

    /**
     * Creates the app directory in APPDATA on Windows, or the user's home folder on linux/macOS.
     */
    private static void makeAppDir() {
        Path soundDir = Paths.get(Util.getSoundDirectory());
        if (!Files.exists(soundDir)) {
            try {
                Files.createDirectories(soundDir);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, "Fatal error when creating dir. \nError is as follows:\n" + e + "\nPlease report this error to the Github Repo.");
                System.exit(3);
            }
        }
    }

    /**
     * Use this to toggle the console logging of the key presses.
     *
     * @param status Determines whether to log the key presses to console or not.
     */
    @Deprecated
    private static void setJNativeLogging(boolean status) {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        if (status) {
            // Enable annoying logger output
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(true);
        } else {
            // Disable annoying logger output
            logger.setLevel(Level.OFF);
            logger.setUseParentHandlers(false);
        }
    }

    /**
     * Returns the soundboard.
     */
    public static SoundBoard getSoundBoard() {
        return soundBoard;
    }

    /**
     * Re-initializes the key listener.
     */
    public static void resetKeyListener() {
        GlobalScreen.removeNativeKeyListener(listener);
        listener = new GlobalKeyListener();
        GlobalScreen.addNativeKeyListener(listener);
    }

}