package net.breakfaststudios.soundboard.interception;

import net.breakfaststudios.util.Util;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class InterceptionInterface {
    public static final String INTERCEPTION_PATH = Util.getMainDirectory() + "interception/" + "interception.dll";
    private int port;
    private final String interceptionDLLDownload = "";
    private DatagramSocket listenerSocket;
    
    public InterceptionInterface() {
        if (!Files.exists(Path.of(INTERCEPTION_PATH))){
            // TODO uncomment this
            // downloadFile();
        }
        port = createNewPort();
        listenerSocket = createSocket();
    }

    public native void interceptionMain(int port);

    public native int deviceID();
    // public native void

    public int getPort(){ return port; }

    public DatagramSocket getListenerSocket(){ return listenerSocket; }

    private int createNewPort(){ return (int) (Math.random() * 16414 + 49152); }

    private DatagramSocket createSocket(){
        DatagramSocket listenerSocket = null;
        try {
            listenerSocket = new DatagramSocket(port, InetAddress.getByName("127.0.0.1"));
        } catch (SocketException | UnknownHostException e) {
            port = createNewPort();
            listenerSocket = createSocket();
            e.printStackTrace();
        }
        return listenerSocket;
    }

    private void downloadFile() {
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
            JOptionPane.showMessageDialog(null, "Failed to download the dll for interceptor.");
        }
    }
}
