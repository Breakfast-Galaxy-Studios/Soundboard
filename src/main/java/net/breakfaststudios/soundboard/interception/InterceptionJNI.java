package net.breakfaststudios.soundboard.interception;

import com.github.malthelegend104.Logger;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Deprecated
public class InterceptionJNI {
    private static final String interceptionDLLDownload = "C:\\Users\\Malcolm\\OneDrive\\Desktop\\interceptiontest\\Project5.dll";
    private static DatagramSocket listenerSocket;
    private static DatagramSocket closingSocket;
    private static DatagramSocket recordingSocket;

    static {
        System.load("C:\\Users\\Malcolm\\OneDrive\\Desktop\\interceptiontest\\Project5.dll");
    }

    private int port;
    private int port2;
    private int port3;

    // Create the port & socket
    public InterceptionJNI() {
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

    /**
     * Get the ints corresponding to the ports
     *
     * @return int[], where index 0 is the listener port, index 1 is the closing port, and index 3 is the recording port.
     */
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
            Logger.err("Port " + port + " was already in use. Trying again. - Listener Socket");
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
            Logger.err("Port " + port2 + " was already in use. Trying again. - Recording Socket");
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
            Logger.err("Port " + port3 + " was already in use. Trying again. - Closing Socket");
            port3 = createNewPort();
            socket = createClosingSocket();
        }
        return socket;
    }
}