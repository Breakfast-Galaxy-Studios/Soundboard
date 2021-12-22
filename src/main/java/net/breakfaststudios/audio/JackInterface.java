package net.breakfaststudios.audio;

import org.jaudiolibs.jnajack.JackException;
import org.jaudiolibs.jnajack.util.SimpleAudioClient;

import java.nio.FloatBuffer;

@Deprecated
public class JackInterface implements SimpleAudioClient.Processor {
    private SimpleAudioClient simpleAudioClient;
    public JackInterface(){
        try {
            simpleAudioClient = SimpleAudioClient.create("BGS-Soundboard", new String[] {"input-L", "input-R"},
                    new String[]{"output-L", "output-R"}, true, true, new JackInterface());
            simpleAudioClient.activate();
        } catch (JackException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup(float v, int i) {

    }

    @Override
    public void process(FloatBuffer[] inputs, FloatBuffer[] outputs) {
        FloatBuffer left = outputs[0];
        FloatBuffer right = outputs[1];
        left.put(inputs[0]);
        right.put(inputs[1]);
    }

    @Override
    public void shutdown() {
        System.out.println("Pass Through shutdown");
    }

    public SimpleAudioClient getSimpleAudioClient() {
        return simpleAudioClient;
    }
}