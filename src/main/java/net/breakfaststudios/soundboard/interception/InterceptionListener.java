package net.breakfaststudios.soundboard.interception;

import com.github.malthelegend104.Logger;
import net.breakfaststudios.BreakfastSounds;
import net.breakfaststudios.soundboard.listeners.GlobalKeyListener;
import net.breakfaststudios.util.Converter;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

final public class InterceptionListener {
    private final AtomicBoolean running = new AtomicBoolean(false);
    /**
     * Initializes the main socket used in the listener
     * This is done here and not later, so it's easier to determine if port is already bound
     */
    private DatagramSocket listenerSocket;
    private DatagramSocket keyBindSocket;
    {
        try {
            listenerSocket = new DatagramSocket(55555, InetAddress.getByName("127.0.0.1"));
            keyBindSocket = new DatagramSocket(55556, InetAddress.getByName("127.0.0.1"));
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, """
                    Another program is using the port needed by interceptor.
                    This can occur when there is already an instance of soundboard running.
                    """);
        }
    }

    private final Thread interceptor = new Thread(() -> {
        // Listen to localhost port 55555
        byte[] receive = new byte[65535];
        GlobalKeyListener keyListener = new GlobalKeyListener();
        DatagramPacket receivePacket;
        while (true) {
            // Create a packet to receive the data
            receivePacket = new DatagramPacket(receive, receive.length);

            // Waits until a packet is received
            try {
                listenerSocket.receive(receivePacket);
                Logger.info(Converter.getKeyText(Integer.parseInt(data(receive))));
                // Checks if the key is registered to a sound, and simulates key up event
                keyListener.interceptionKeyRegister(Integer.parseInt(data(receive)));
                keyListener.interceptionKeyReleased(Integer.parseInt(data(receive)));

                // Break the loop if the other program ends.
                if (data(receive).equals("FATALERROR")) {
                    Logger.err("Fatal error occurred with BGS-Macros.");
                    break;
                }
                receive = new byte[65535];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listenerSocket.close();
    });

    /**
     * Converts a byte array into a string
     *
     * @param udpPacket byte[] from a socket, all bytes are converted to chars
     * @return String of all the chars in the byte array.
     */
    private static String data(byte[] udpPacket) {
        if (udpPacket == null)
            return null;
        StringBuilder data = new StringBuilder();
        int i = 0;
        while (udpPacket[i] != 0) {
            data.append((char) udpPacket[i]);
            i++;
        }
        return data.toString();
    }

    public void startInterceptor() {
        running.set(true);
        interceptor.start();
    }

    @Deprecated
    public void startInterceptorDeprecated() {
        new Thread(() -> {
            // Listen to localhost port 55555
            byte[] receive = new byte[65535];
            GlobalKeyListener keyListener = new GlobalKeyListener();
            DatagramPacket receivePacket;
            while (true) {
                // Create a packet to receive the data
                receivePacket = new DatagramPacket(receive, receive.length);

                // Waits until a packet is received
                try {
                    listenerSocket.receive(receivePacket);
                    Logger.info(Converter.getKeyText(Integer.parseInt(data(receive))));

                    // Checks if the key is registered to a sound, and simulates key up event
                    keyListener.interceptionKeyRegister(Integer.parseInt(data(receive)));
                    keyListener.interceptionKeyReleased(Integer.parseInt(data(receive)));

                    // Break the loop if the other program ends.
                    if (data(receive).equals("FATALERROR")) {
                        Logger.err("Fatal error occurred \"serverside\".");
                        break;
                    }
                    receive = new byte[65535];
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Throw error if other program ends.
            try {
                throw new Exception("Lost connection to interceptor.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
        }).start();
    }

    public String getNextKeycode() throws Exception {
        try {
            byte[] receive = new byte[65535];
            // Create a packet to receive the data
            DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);

            // Waits until a packet is received
            keyBindSocket.receive(receivePacket);
            // Break the loop if the other program ends.
            if (data(receive).equals("FATALERROR")) {
                Logger.err("Fatal error occurred \"serverside\".");
                JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
                keyBindSocket.close();
                throw new Exception("Lost connection to interceptor.");
            }
            keyBindSocket.close();
            return data(receive);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
            throw new Exception("Lost connection to interceptor.");
        }
    }
}