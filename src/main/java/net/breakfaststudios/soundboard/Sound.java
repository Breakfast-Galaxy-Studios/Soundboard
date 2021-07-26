package net.breakfaststudios.soundboard;

public class Sound {

    private Integer[] keys;
    private String name;
    private String path;
    private float volume;


    public Sound(String soundName, String path, Integer[] keys, float volume) {
        this.name = soundName;
        this.path = path;
        this.volume = volume;
        this.keys = keys;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public Integer[] getKeys() { return keys; }

    public void setKeys(Integer[] newKeys){ this.keys = newKeys;}
}
