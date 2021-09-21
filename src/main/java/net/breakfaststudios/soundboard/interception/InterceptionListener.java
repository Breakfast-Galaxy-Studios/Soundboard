package net.breakfaststudios.soundboard.interception;

import net.breakfaststudios.soundboard.listeners.GlobalKeyListener;
import net.breakfaststudios.util.Converter;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class InterceptionListener{

    /**
     * Initializes the main socket used in the listener
     */
    private DatagramSocket listenerSocket;
    {
        try {
            listenerSocket = new DatagramSocket(55555, InetAddress.getByName("127.0.0.1"));
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a byte array into a string
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
        new Thread(()->{
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
                    System.out.println(Converter.getKeyText(Integer.parseInt(data(receive))));

                    // Checks if the key is registered to a sound, and simulates key up event
                    keyListener.interceptionKeyRegister(Integer.parseInt(data(receive)));
                    keyListener.interceptionKeyReleased(Integer.parseInt(data(receive)));

                    // Break the loop if the other program ends.
                    if (data(receive).equals("FATALERROR")) {
                        System.out.println("Fatal error occurred \"serverside\".");
                        break;
                    }
                    receive = new byte[65535];
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Throw error if other program ends.
            try { throw new Exception("Lost connection to interceptor."); } catch (Exception e) { e.printStackTrace(); }
            JOptionPane.showMessageDialog(null, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
        }).start();
    }

    public void closeProgram() throws IOException {
        // Send tcp packet via localhost:55556 to close the key listener
        Socket socket = new Socket("127.0.0.1", 58585);
        OutputStream output = socket.getOutputStream();
        byte[] buffer = "EXIT0".getBytes();
        output.write(buffer);
        System.out.println("Closed the interceptor.");
    }

    public String listenForDevID() throws Exception{
        try{
            byte[] receive = new byte[65535];
            DatagramPacket receivePacket;
            DatagramSocket dsDevID = new DatagramSocket(55554, InetAddress.getByName("127.0.0.1"));


            // Create a packet to receive the data
            receivePacket = new DatagramPacket(receive, receive.length);

            // Waits until a packet is received
            dsDevID.receive(receivePacket);
            // Break the loop if the other program ends.
            if (data(receive).equals("FATALERROR")) {
                System.out.println("Fatal error occurred \"serverside\".");
                JOptionPane.showMessageDialog(null, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
                dsDevID.close();
                throw new Exception("Lost connection to interceptor.");
            }
            dsDevID.close();
            return String.valueOf(data(receive));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
            throw new Exception("Lost connection to interceptor.");
        }
    }


    public String getNextKeycode() throws Exception {
        try{
            byte[] receive = new byte[65535];
            DatagramSocket ds = new DatagramSocket(55556, InetAddress.getByName("127.0.0.1"));

            // Create a packet to receive the data
            DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);

            // Waits until a packet is received
            ds.receive(receivePacket);
            // Break the loop if the other program ends.
            if (data(receive).equals("FATALERROR")) {
                System.out.println("Fatal error occurred \"serverside\".");
                JOptionPane.showMessageDialog(null, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
                ds.close();
                throw new Exception("Lost connection to interceptor.");
            }
            ds.close();
            return data(receive);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fatal Error From Interceptor.\nRestart the program.\nIf this error keeps occurring please contact us on the GitHub Repo.");
            throw new Exception("Lost connection to interceptor.");
        }
    }
}