package net.breakfaststudios.audio;

import net.breakfaststudios.util.Util;

import java.io.*;
import java.util.Properties;

public class AudioInterface {

    /**
     *
     * @return  0 if using clips, normal audio
     *          2 if using jack audio connection kit interface
     */
    public static int getAudioInterface(){
        Properties prop = getAudioSettings();
        if (prop.getProperty("interface").equals("0")) return 0;
        if (prop.getProperty("interface").equals("0")) return 0;
        return -1;
    }

    public static Properties getAudioSettings(){
        try {
            FileInputStream file = new FileInputStream(Util.getMainDirectory() + "audio.properties");
            Properties prop = new Properties();
            prop.load(file);
            file.close();
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            return updateAudioSettings(0);
        }
    }

    public static Properties updateAudioSettings(int type){
        File audioSettingsFile = new File(Util.getMainDirectory() + "audio.properties");
        Properties audioSettings = new Properties();

        // Audio Interface type
        audioSettings.setProperty("interface", String.valueOf(type));

        if (!audioSettingsFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                audioSettingsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream output = new FileOutputStream(audioSettingsFile.getPath());
            audioSettings.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return audioSettings;
    }
}
