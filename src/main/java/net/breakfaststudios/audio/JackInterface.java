package net.breakfaststudios.audio;

import org.jaudiolibs.jnajack.JackException;
import org.jaudiolibs.jnajack.util.SimpleAudioClient;

import java.io.File;
import java.io.FileInputStream;
import java.nio.FloatBuffer;

@Deprecated
public class JackInterface implements SimpleAudioClient.Processor {
    private SimpleAudioClient simpleAudioClient;
    private float[] data;

    public JackInterface() {
        try {
            simpleAudioClient = SimpleAudioClient.create("BGS-Soundboard", new String[]{"input-L", "input-R"},
                    new String[]{"output-L", "output-R"}, true, true, this);
            simpleAudioClient.activate();
        } catch (JackException e) {
            e.printStackTrace();
        }
    }

    public static FloatBuffer toBuffer(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            byte[] arr = fs.readAllBytes();
            FloatBuffer buf = FloatBuffer.allocate(arr.length);
            for (byte to : arr) {
                buf.put(to);
            }
            return buf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FloatBuffer[] toBuffer(File... files) {

        try {
            FloatBuffer[] buffers = new FloatBuffer[files.length];
            for (int i = 0; i < files.length; i++) {
                FileInputStream fs = new FileInputStream(files[i]);
                byte[] arr = fs.readAllBytes();
                FloatBuffer buf = FloatBuffer.allocate(arr.length);
                for (byte to : arr) {
                    buf.put(to);
                }
                buffers[i] = buf;
            }
            return buffers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setup(float sampleRate, int bufferSize) {
        data = new float[bufferSize];
    }

    public void process(FloatBuffer[] inputs, FloatBuffer[] outputs) {
        FloatBuffer left = outputs[0];
        FloatBuffer right = outputs[1];
        int size = left.capacity();
        int left_phase = 0;
        int right_phase = 0;

        for (int i = 0; i < size; i++) {
            left.put(i, data[left_phase]);
            right.put(i, data[right_phase]);
            left_phase++;
            right_phase++;
        }
    }

    /*U
    @Override
    public void process(FloatBuffer[] inputs, FloatBuffer[] outputs) {
        FloatBuffer left = outputs[0];
        FloatBuffer right = outputs[1];
        left.put(inputs[0]);
        right.put(inputs[1]);
    }
*/
    @Override
    public void shutdown() {
        System.out.println("Pass Through shutdown");
    }

    public SimpleAudioClient getSimpleAudioClient() {
        return simpleAudioClient;
    }
}