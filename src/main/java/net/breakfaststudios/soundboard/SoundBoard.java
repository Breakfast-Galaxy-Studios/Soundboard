package net.breakfaststudios.soundboard;

import net.breakfaststudios.audio.AudioInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This is the main cache class and stores all the sounds
 */
public class SoundBoard {
    private final List<Sound> sounds;
    private final ThreadPoolExecutor executorService;
    private final byte audioInterfaceType;
    // private final JackInterface jack;

    /**
     * Sound cache class
     */
    public SoundBoard() {
        sounds = new ArrayList<>();
        audioInterfaceType = AudioInterface.getAudioInterface();
        this.executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        // jack = new JackInterface();
    }

    /**
     * Queues sound for playing.
     *
     * @param sound Sound to play
     */
    public void queueSound(Sound sound) {
        executorService.execute(new SoundThread(sound.getPath(), sound.getVolume(), sound.getLength(), audioInterfaceType));
    }

    /**
     * Adds a sound to the cache
     *
     * @param sound Sound to add to the cache
     */
    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    /**
     * Removes a sound from the cache
     *
     * @param key Sound to remove from the cache
     */
    public void removeSound(Sound key) {
        sounds.remove(key);
    }

    /**
     * Gets a sound by the name if it exists.
     *
     * @param name Name of sound to get
     * @return Sound (can be null if it doesn't exist)
     */
    public Sound getSound(String name) {
        for (Sound sound : sounds) {
            if (sound.getName().equalsIgnoreCase(name))
                return sound;
        }
        return null;
    }

    /*
    public JackInterface getJack() {
        return jack;
    }
    */

    /**
     * Get all sounds in the cache
     *
     * @return List<Sound>
     */
    public List<Sound> getSounds() {
        return sounds;
    }
}
