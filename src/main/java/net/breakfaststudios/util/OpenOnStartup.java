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
        InputStream is = Util.class.getClassLoader().getResourceAsStream("soundBoardLinux.sh");
        File file = new File("/etc/init.d/soundboard");
        if (bool) {
            try {
                boolean dirCreated;

                if (!file.exists()) {
                    dirCreated = file.mkdirs();
                    Files.createFile(Paths.get(file.getPath()));
                } else {
                    return;
                }

                if (!dirCreated || is == null) throw new IOException("Startup Directory could not be created.");
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(fileWriter);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.equals("PATH_TO_JAR=%JAR_PATH%")) {
                        line.replace("%JAR_PATH%", BreakfastSounds.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "soundboard.jar");
                    }
                    out.append(line);
                    out.newLine();
                }
                reader.close();
                out.close();

                /*
                 * Create a command to make the file executable
                 * sudo chmod +x /etc/init.d/soundboard
                 * This probably requires sudo
                 * This DEFINITELY needs more testing
                 */
                String[] createFileCommand = {"sudo", "chmod", "+x", "/etc/init.d/soundboard"};
                Runtime.getRuntime().exec(createFileCommand);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to remove from startup folder.");
                ex.printStackTrace();
            }
        }
    }

}
