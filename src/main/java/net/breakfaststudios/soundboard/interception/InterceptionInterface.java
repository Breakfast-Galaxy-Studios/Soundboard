package net.breakfaststudios.soundboard.interception;

import net.breakfaststudios.BreakfastSounds;

import javax.swing.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Deprecated
public class InterceptionInterface {
    // public static final String INTERCEPTION_PATH = Util.getMainDirectory() + "interception/" + "interception.dll";
    // private final String interceptionDLLDownload = "";
    private static DatagramSocket listenerSocket;
    private static DatagramSocket closingSocket;
    private static DatagramSocket recordingSocket;

    // Load the lib
    static {
        try {
            // if (!Files.exists(Path.of(INTERCEPTION_PATH))){
            // TODO uncomment this
            // downloadFile();
            // }
            System.load("C:\\Users\\Malcolm\\OneDrive\\Desktop\\interceptiontest\\Project5.dll");
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, "Interception.dll could not be found/installed. Interception will not work without this.");
        }
    }

    private int port;
    private int port2;
    private int port3;

    // Create the port & socket
    public InterceptionInterface() {
        port = createNewPort();
        port2 = createNewPort();
        port3 = createNewPort();
        listenerSocket = createListenerSocket();
        closingSocket = createClosingSocket();
        recordingSocket = createRecordingSocket();
    }

    public static DatagramSocket getListenerSocket() {
        return listenerSocket;
    }

    public static DatagramSocket getClosingSocket() {
        return closingSocket;
    }

    public static DatagramSocket getRecordingSocket() {
        return recordingSocket;
    }

    public native long getDeviceID();

    public native void interception(int port, int port2, int port3, long devID);

    public void restartInterception() {
        port = createNewPort();
        port2 = createNewPort();
        port3 = createNewPort();
        listenerSocket = createListenerSocket();
        closingSocket = createClosingSocket();
        recordingSocket = createRecordingSocket();
    }

    public int[] getPorts() {
        return new int[]{port, port2, port3};
    }

    private int createNewPort() {
        return (int) (Math.random() * 16414 + 49152);
    }

    private DatagramSocket createListenerSocket() {
        DatagramSocket listenerSocket;
        try {
            listenerSocket = new DatagramSocket(port, InetAddress.getByName("127.0.0.1"));
        } catch (SocketException | UnknownHostException e) {
            // e.printStackTrace();
            System.err.println("Port " + port + " was already in use. Trying again. - Listener Socket");
            port = createNewPort();
            listenerSocket = createListenerSocket();
        }
        return listenerSocket;
    }

    private DatagramSocket createRecordingSocket() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(port2, InetAddress.getByName("127.0.0.1"));
        } catch (SocketException | UnknownHostException e) {
            // e.printStackTrace();
            System.err.println("Port " + port2 + " was already in use. Trying again. - Recording Socket");
            port2 = createNewPort();
            socket = createListenerSocket();
        }
        return socket;
    }

    private DatagramSocket createClosingSocket() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(port3, InetAddress.getByName("127.0.0.1"));
        } catch (Exception e) {
            // e.printStackTrace();
            System.err.println("Port " + port3 + " was already in use. Trying again. - Closing Socket");
            port3 = createNewPort();
            socket = createClosingSocket();
        }
        return socket;
    }

    /* private void downloadFile() {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(interceptionDLLDownload).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(InterceptionInterface.INTERCEPTION_PATH + "interception.dll");
            System.out.println("Downloading Interception DLL.");
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Done downloading DLL.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(BreakfastSounds.dialogParent, "Failed to download the dll for interceptor.");
        }
    }*/
}
