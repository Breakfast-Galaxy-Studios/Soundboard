package net.breakfaststudios.soundboard.interception;

import net.breakfaststudios.util.Util;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is the main class for interception.
 * It is intended to be used to interface with everything for interceptor.
 */
public class InterceptionMain {
    /**
     * Public InterceptionListener class so that other classes & methods can all modify and use the same object.
     * You should never create another InterceptionListener Object.
     */
    public static InterceptionListener interceptionListener = new InterceptionListener();

    /**
     * The directory for the interception program, and it's required dll's
     */
    public static String interceptionDir = Util.getMainDirectory() + "interception/";

    /**
     * Path in the form of a string, of the interception.properties file
     */
    public static String interceptionSettingsFilePath = Util.getMainDirectory() + "interception.properties";

    /**
     * Checks if the os is windows, and makes sure that interception is turned on
     * If these conditions are met, it then calls the startInterception method.
     */
    public static void initKeyboardListener() {
        if (Util.os.contains("win") && Util.getInterceptionSettings().getProperty("interception").equals("true")) {
            try {
                startInterception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the interceptor thread, and opens interception.exe
     */
    public static void startInterception(){
        // Todo, add to this method something to run the vbs script to start interceptor
        interceptionListener.startInterceptor();
    }


    /**
     * Creates the interception.properties file, used to store information needed by both soundboard and the interception program.
     * @param devID The device id of the keyboard the interceptor needs to intercept.
     * @return boolean, true if file was created successfully, false if not.
     */
    public static boolean updateInterceptionProperties(String devID){
        Properties prop = new Properties();
        File interceptionFile = new File(interceptionSettingsFilePath);
        prop.setProperty("devID", devID);

        // Create file if it doesn't already exist
        if (!interceptionFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                interceptionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream output = new FileOutputStream(interceptionFile.getPath());
            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Creates a Visual Basic Script, so that interceptor can be launched silently, without having a cmd window.
     * @return Returns true if file was created, false otherwise
     */

    public static boolean createInterceptionVBS(String pathToInterception){
        String script = "Set WshShell = CreateObject(\"WScript.Shell\") \n" + "WshShell.Run chr(34) & \"" + pathToInterception + "\" & Chr(34), 0\n" + "Set WshShell = Nothing";
        return true;
    }

    /**
     * Delete everything having to do with interception.
     */
    public static void deleteInterception(){
        // Delete startup script, interception.properties, and the entire interception folder.
        try{
            Files.deleteIfExists(Path.of(interceptionSettingsFilePath));
            Files.deleteIfExists(Path.of(interceptionDir));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, """
              Failed to delete all the contents of interceptor.
              If this error keeps occurring, please delete it yourself.
              The path to the folder is as follows:
              """ + interceptionDir);
        }
    }
}
