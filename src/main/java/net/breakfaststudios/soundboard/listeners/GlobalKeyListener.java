package net.breakfaststudios.soundboard.listeners;

import net.breakfaststudios.BreakfastSounds;
import net.breakfaststudios.soundboard.Sound;
import net.breakfaststudios.soundboard.SoundBoard;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (!currentlyPressedKeys.contains(pressedKey.getKeyCode())) {
            currentlyPressedKeys.add(pressedKey.getKeyCode());
        }
        registerSound();
    }

    /**
     * Allows the same registry to be used by the keyboard interceptor.
     *
     * @param keycode int - The keycode of the key that was pressed.
     */
    public void interceptionKeyRegister(int keycode) {
        if (!currentlyPressedKeys.contains(keycode)) {
            currentlyPressedKeys.add(keycode);
        }
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

                for (int i = 0; i < currentlyPressedKeys.size(); i++) {
                    if (neededKeys.size() != 0 && Objects.equals(neededKeys.get(0), currentlyPressedKeys.get(i))) {
                        neededKeys.remove(0);
                        if (!Objects.equals(neededKeys.get(0), currentlyPressedKeys.get(i + 1))) break;
                        else neededKeys.remove(0);
                    }
                }

                if (neededKeys.size() == 0) {
                    soundBoard.queueSound(sound);
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
