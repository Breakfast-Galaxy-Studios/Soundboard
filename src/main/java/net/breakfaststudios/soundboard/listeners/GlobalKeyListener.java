package net.breakfaststudios.soundboard.listeners;

import net.breakfaststudios.BreakfastSounds;
import net.breakfaststudios.soundboard.Sound;
import net.breakfaststudios.soundboard.SoundBoard;
import net.breakfaststudios.soundboard.SoundThread;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalKeyListener implements NativeKeyListener {

    public static ArrayList<Integer> currentlyPressedKeys = new ArrayList<>();
    private final SoundBoard soundBoard;

    public GlobalKeyListener() {
        soundBoard = BreakfastSounds.getSoundBoard();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!currentlyPressedKeys.contains(e.getKeyCode()))
            currentlyPressedKeys.add(e.getKeyCode());
        try {
            for (Sound sound : soundBoard.getSounds()) {
                List<Integer> neededKeys = new ArrayList<>(Arrays.asList(sound.getKeys()));
                for (int key : currentlyPressedKeys) {
                    if (neededKeys.get(0) == key && neededKeys.size() != 1) {
                        neededKeys.remove((Object) key);
                    } else if (neededKeys.get(0) == key && neededKeys.size() == 1) {
                        neededKeys.clear();
                    } else if (neededKeys.contains(key)) {
                        break;
                    }
                }
                if (neededKeys.size() == 0) {
                    SoundThread t = new SoundThread(sound.getPath(), sound.getVolume());
                    t.start();
                }

            }
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        currentlyPressedKeys.remove((Object) e.getKeyCode());
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
