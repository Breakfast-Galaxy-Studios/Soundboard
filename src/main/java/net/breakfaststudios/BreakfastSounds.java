package net.breakfaststudios;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import net.breakfaststudios.soundboard.SoundBoard;
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
    public static final String currentVersion = "v2.3";
    public static String SELECTED_AUDIO_DEVICE;
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
    public static void main(String[] args) {
        dialogParent.setAlwaysOnTop(true);
        soundBoard = new SoundBoard();
        Properties settings = null;

        /*
         * Make sure AutoUpdater is deleted if it exists.
         */
        if (Files.exists(Path.of(Util.mainDir + "autoupdater.jar"))) {
            try {
                Files.delete(Path.of(Util.mainDir + "autoupdater.jar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Updater.runAutoUpdater();

        if (!Files.exists(Path.of(Util.mainDir + "settings.properties"))) {
            String soundOutput = "Primary Sound Driver";
            Util.updateSettings(soundOutput, false, false, false, false);
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
                    validateSettings.setProperty(keyAsString, "false");
                } else {
                    if (key.equals("soundOutput")) {
                        validateSettings.setProperty(keyAsString, settings.getProperty("soundOutput"));
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
                        Boolean.parseBoolean(validateSettings.getProperty("openOnStartup"))
                );
            }
        }

        // UI Scaling will be slightly messed up outside of a Windows OS.
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore) {
            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (Exception ignored) {
            }
        }

        // Create and display the UI
        makeAppDir();
        EventQueue.invokeLater(BreakfastSounds::new);

        // Register type of keylistener
        initKeyListener();

        //Load some stuff from settings

        if (settings != null) {
            SELECTED_AUDIO_DEVICE = settings.getProperty("soundOutput");
        } else {
            // Makes new config if it doesn't exist
            SELECTED_AUDIO_DEVICE = "Primary Sound Driver";
        }

        //Disable annoying logger output
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        //Register key listeners
        listener = new GlobalKeyListener();
        GlobalScreen.addNativeKeyListener(listener);

        startAutoCollection(300000);
    }

    /**
     * Starts the auto garbage collection thread hehe
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
                System.out.println("RUNNING COLLECTION");
                Runtime.getRuntime().gc();
            }
        }).start();
    }


    /**
     * Initializes the type of keylistener to be used.
     */
    private static void initKeyListener() {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());
                System.exit(53);
            }
    }

    /**
     * Creates the app directory in APPDATA on Windows, or the user's home folder on linux/macOS.
     */
    private static void makeAppDir() {
        Path soundDir = Paths.get(Util.soundDir);
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