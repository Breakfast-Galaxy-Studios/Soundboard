package net.breakfaststudios.util;

import net.breakfaststudios.BreakfastSounds;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.breakfaststudios.util.Util.*;

public class OpenOnStartup {
    /**
     * Changes open to startup settings.
     * @param bool True if you want it to open on startup, else false.
     */
    public static void openOnStartup(boolean bool){
        if (os.contains("win")) {
            windows(bool);
        } else if (os.equals("mac")) {
            mac(bool);
        } else if (os.contains("nux")) {
            linux(bool);
        } else {
            JOptionPane.showMessageDialog(null, "This application currently doesn't support openOnStartup for this operating system.\nIf you think this is an error, or would like to request this feature be added for your OS, please create an issue on the GitHub Repo.");
        }
    }

    private static void windows(boolean bool){
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

    private static void mac(boolean bool){
        // TODO implement this
    }

    private static void linux(boolean bool){
        // TODO: implement linux support here
        // TODO: TEST LINUX SUPPORT
        InputStream is = Util.class.getClassLoader().getResourceAsStream("soundboard.service");
        File file = new File("/etc/systemd/system/soundboard.service");
        if (bool) {
            try {
                boolean dirCreated;
                
                // Make sure the dirs exist and create the file
                if (!file.exists()) {
                    dirCreated = file.mkdirs();
                    Files.createFile(Paths.get(file.getPath()));
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
                String[] enableService =  {"sudo", "systemctl", "enable", "soundboard"};
                runtime.exec(enableService);
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
