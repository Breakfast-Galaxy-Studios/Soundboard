package net.breakfaststudios.soundboard.listeners;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This is the keybind recording class for the regular keybind recording mode.
 */
public class KeybindRecorder implements NativeKeyListener {

    public static ArrayList<Integer> keyBindKeys = new ArrayList<>();
    public static boolean isRecording = false;
    private static JDialog recorderWindow;
    private static JTextField keybindText;
    private static KeybindRecorder keybindRecorder;
    private static NativeKeyEvent startingKey;

    /**
     * Starts the keybind recorder
     *
     * @param dialogBox Dialog box to show
     * @param label     Text in the box
     */
    public static void startRecording(JDialog dialogBox, JTextField label) {
        isRecording = true;
        recorderWindow = dialogBox;
        keybindText = label;
        keybindRecorder = new KeybindRecorder();

        keyBindKeys.clear();

        if (recorderWindow.isAlwaysOnTopSupported()) {
            recorderWindow.setAlwaysOnTop(true);
        }
        recorderWindow.setVisible(true);

        GlobalScreen.addNativeKeyListener(keybindRecorder);
    }

    /**
     * Cancels the keybind recording
     */
    public static void cancelKeybindRecording() {
        isRecording = false;
        GlobalScreen.removeNativeKeyListener(keybindRecorder);
        recorderWindow.setVisible(false);
        recorderWindow.setAlwaysOnTop(false);

        keyBindKeys.clear();
        keybindRecorder = null;
        startingKey = null;
        recorderWindow = null;
        keybindText = null;
    }

    /**
     * Records key presses
     *
     * @param e The key that was pressed
     */
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!keyBindKeys.contains(e.getKeyCode())) {
            keyBindKeys.add(e.getKeyCode());

            if (keyBindKeys.size() == 1) {
                startingKey = e;
            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    /**
     * Listens for key releases and checks if it should stop recording
     *
     * @param e Key that was released
     */
    public void nativeKeyReleased(NativeKeyEvent e) {

        if (startingKey != null && e.getKeyCode() == startingKey.getKeyCode()) {
            isRecording = false;
            GlobalScreen.removeNativeKeyListener(keybindRecorder);
            recorderWindow.setVisible(false);
            recorderWindow.setAlwaysOnTop(false);

            //
            // Build the text for the Key/s box
            //
            StringBuilder text = new StringBuilder();
            for (int key : keyBindKeys) {
                text.append(NativeKeyEvent.getKeyText(key)).append("_");
            }
            text.replace(text.length() - 1, text.length(), "");

            //
            // Set the text for the Key/s box
            //
            keybindText.setText(text.toString());

            //
            // Reset the static fields
            //
            keyBindKeys.clear();
            keybindRecorder = null;
            startingKey = null;
            recorderWindow = null;
            keybindText = null;
        }
    }
}