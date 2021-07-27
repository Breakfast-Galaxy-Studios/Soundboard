package net.breakfaststudios.util;

import net.breakfaststudios.BreakfastSounds;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import static net.breakfaststudios.util.Util.os;

public class Updater {
    private static final String fileName = "soundboard.jar";
    private static final String autoUpdaterPath = Util.getMainDirectory() + "autoupdater.jar";
    private static final String autoUpdaterURL = "https://github.com/Breakfast-Galaxy-Studios/Universial-Auto-Updater/releases/download/v1.0/autoupdater.jar";

    public static void runAutoUpdater() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.github.com/repos/Breakfast-Galaxy-Studios/Soundboard/releases/latest");
            request.addHeader("accept", "application/vnd.github.v3+json");
            CloseableHttpResponse result = httpClient.execute(request);
            String js = EntityUtils.toString(result.getEntity(), "UTF-8");
            result.close();
            httpClient.close();
            JSONObject json = new JSONObject(js);
            String version = json.getString("tag_name");
            if (!version.equals(BreakfastSounds.currentVersion)) {
                JOptionPane updatePrompt = new JOptionPane("");
                updatePrompt.setMessageType(JOptionPane.YES_NO_OPTION);
                updatePrompt.setVisible(true);
                int updateResult = JOptionPane.showConfirmDialog(null, "A new update is available. Would you like to update?", "New Soundboard Update Available",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (updateResult == JOptionPane.YES_OPTION) {
                    Updater.updater(version);
                }
            }
        } catch (IOException ignored) {
        }
    }

    private static void updater(String newVersion) {
        try {
            // Get operating path of current jar
            final String jarPath = BreakfastSounds.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            // Download url from github
            final String updateURL = "https://github.com/Breakfast-Galaxy-Studios/Soundboard/releases/download/" + newVersion + "/soundboard.jar";

            new Thread(() -> {
                downloadFile(Util.getMainDirectory());
                try {
                    // remove soundboard.jar from operating path, remove / from beginning if on windows
                    String[] newPath = jarPath.split("/");
                    String[] operatingPathArray = Arrays.copyOf(newPath, newPath.length - 1);
                    StringBuilder operatingPath = new StringBuilder(String.join("/", operatingPathArray));
                    if (os.contains("win")) {
                        operatingPath.deleteCharAt(0);
                    }
                    operatingPath.append("/");

                    // Arguments for autoupdater: updateURL(URL for new release), saveDir(directory to write to), fileName (name of new file)
                    String[] autoUpdater = {"java", "-jar", autoUpdaterPath, updateURL, operatingPath.toString(), fileName};
                    Runtime.getRuntime().exec(autoUpdater);
                    System.out.println(Arrays.toString(autoUpdater));

                    // Make sure program closes itself
                    System.exit(47);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Failed to update the application.");
                    e.printStackTrace();
                }
            }).start();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String saveDir) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(Updater.autoUpdaterURL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(saveDir + "autoupdater.jar")) {
            System.out.println("Downloading Updater.");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Done downloading Updater.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to update your application.");
        }
    }
}