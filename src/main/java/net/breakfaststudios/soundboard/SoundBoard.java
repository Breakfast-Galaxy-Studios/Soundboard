package net.breakfaststudios.soundboard;

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

    /**
     * Sound cache class
     */
    public SoundBoard() {
        sounds = new ArrayList<>();
        this.executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    /**
     * Queues sound for playing.
     *
     * @param runnable ooga booga!
     */
    public void queueSound(SoundThread runnable) {
        executorService.execute(runnable);
        //System.out.println(executorService.getPoolSize());
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

    /**
     * Get all sounds in the cache
     *
     * @return List<Sound>
     */
    public List<Sound> getSounds() {
        return sounds;
    }
}
