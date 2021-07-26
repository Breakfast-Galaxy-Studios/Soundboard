package net.breakfaststudios.soundboard;

import java.util.ArrayList;
import java.util.List;

public class SoundBoard {

    private final List<Sound> sounds;
    private String SELECTED_AUDIO_DEVICE;

    public SoundBoard() {
        sounds = new ArrayList<>();
    }

    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    public void removeSound(Sound key) {
        sounds.remove(key);
    }

    public Sound getSound(String name) {
        for (Sound sound : sounds) {
            if (sound.getName().equalsIgnoreCase(name))
                return sound;
        }
        return null;
    }

    public String getSelectedAudioDevice() {
        return SELECTED_AUDIO_DEVICE;
    }

    public void setSelectedAudioDevice(String selectedAudioDevice) {
        SELECTED_AUDIO_DEVICE = selectedAudioDevice;
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}
