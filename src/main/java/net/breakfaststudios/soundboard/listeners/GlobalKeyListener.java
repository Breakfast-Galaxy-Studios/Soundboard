package net.breakfaststudios.soundboard.listeners;

import net.breakfaststudios.BreakfastSounds;
import net.breakfaststudios.soundboard.Sound;
import net.breakfaststudios.soundboard.SoundBoard;
import net.breakfaststudios.soundboard.SoundThread;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class holds the method of playing sounds if the correct keys are pressed
 */
public class GlobalKeyListener implements NativeKeyListener {

    private final ArrayList<Integer> currentlyPressedKeys = new ArrayList<>();
    private final SoundBoard soundBoard;

    public GlobalKeyListener() {
        soundBoard = BreakfastSounds.getSoundBoard();
    }

    /**
     * Listens for all key presses and plays sound if requirements are met
     *
     * @param pressedKey Key that was pressed
     */
    public void nativeKeyPressed(NativeKeyEvent pressedKey) {
        if (!currentlyPressedKeys.contains(pressedKey.getKeyCode()))
            currentlyPressedKeys.add(pressedKey.getKeyCode());
        registerSound();
    }

    /**
     * Allows the same registry to be used by the keyboard interceptor.
     *
     * @param keycode int - The keycode of the key that was pressed.
     */
    public void interceptionKeyRegister(int keycode) {
        if (!currentlyPressedKeys.contains(keycode))
            currentlyPressedKeys.add(keycode);
        registerSound();
    }

    /**
     * Plays the sound if all the keys needed are pressed.
     */
    private void registerSound() {
        try {
            List<Integer> neededKeys = new ArrayList<>();
            for (Sound sound : soundBoard.getSounds()) {

                Collections.addAll(neededKeys, sound.getKeys());

                for (int key : currentlyPressedKeys) {
                    if (neededKeys.get(0) == key) {
                        neededKeys.remove((Integer) key);
                    } else if (neededKeys.contains(key)) {
                        break;
                    }
                }

                if (neededKeys.size() == 0) {
                    new SoundThread(sound.getPath(), sound.getVolume(), sound.getLength()).start();
                }
                neededKeys.clear();
            }
        } catch (IndexOutOfBoundsException ignored) { }
    }

    /**
     * Listens for key releases and removes that key from the pressed keys cache
     * @param releasedKey Key that was released
     */
    public void nativeKeyReleased(NativeKeyEvent releasedKey) {
        currentlyPressedKeys.remove((Integer) releasedKey.getKeyCode());
    }
    /**
     * Deletes stored interception key from cache
     * @param releasedKey Key that was released
     */
    public void interceptionKeyReleased(Integer releasedKey) {
        currentlyPressedKeys.remove(releasedKey);
    }

    public void nativeKeyTyped(NativeKeyEvent e) { }
}
