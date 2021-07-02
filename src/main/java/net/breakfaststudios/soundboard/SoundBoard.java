package net.breakfaststudios.soundboard;

import java.util.ArrayList;
import java.util.List;

public class SoundBoard {

    private final List<Sound> sounds;

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

    public List<Sound> getSounds() {
        return sounds;
    }
}
