package net.breakfaststudios.soundboard.interception;

import net.breakfaststudios.util.Util;

public class InterceptionInterface {
    static {
        System.load(Util.getMainDirectory() + "interception/" + "interception.dll");
    }

    public native void interceptionMain();
    public native int deviceID();
    // public native void
}
