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

public class GlobalKeyListener implements NativeKeyListener {

    private final ArrayList<Integer> currentlyPressedKeys = new ArrayList<>();
    private final SoundBoard soundBoard;

    public GlobalKeyListener() {
        soundBoard = BreakfastSounds.getSoundBoard();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!currentlyPressedKeys.contains(e.getKeyCode()))
            currentlyPressedKeys.add(e.getKeyCode());
        if(currentlyPressedKeys.isEmpty())
            return;
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
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        currentlyPressedKeys.remove((Integer) e.getKeyCode());
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
