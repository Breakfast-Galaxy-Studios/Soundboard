package net.breakfaststudios.util;

import java.awt.*;

public class Converter {
    public static int getKeyCode(String var0) {

        if (Toolkit.getProperty("AWT.undefined", "Undefined").equals(var0)) {
            return 0;
        } else if (Toolkit.getProperty("AWT.escape", "Escape").equals(var0)) {
            return 1;
        } else if ("1".equals(var0)) {
            return 2;
        } else if ("2".equals(var0)) {
            return 3;
        } else if ("3".equals(var0)) {
            return 4;
        } else if ("4".equals(var0)) {
            return 5;
        } else if ("5".equals(var0)) {
            return 6;
        } else if ("6".equals(var0)) {
            return 7;
        } else if ("7".equals(var0)) {
            return 8;
        } else if ("8".equals(var0)) {
            return 9;
        } else if ("9".equals(var0)) {
            return 10;
        } else if ("0".equals(var0)) {
            return 11;
        } else if (Toolkit.getProperty("AWT.minus", "Minus").equals(var0)) {
            return 12;
        } else if (Toolkit.getProperty("AWT.equals", "Equals").equals(var0)) {
            return 13;
        } else if (Toolkit.getProperty("AWT.backSpace", "Backspace").equals(var0)) {
            return 14;
        } else if (Toolkit.getProperty("AWT.tab", "Tab").equals(var0)) {
            return 15;
        } else if ("Q".equals(var0)) {
            return 16;
        } else if ("W".equals(var0)) {
            return 17;
        } else if ("E".equals(var0)) {
            return 18;
        } else if ("R".equals(var0)) {
            return 19;
        } else if ("T".equals(var0)) {
            return 20;
        } else if ("Y".equals(var0)) {
            return 21;
        } else if ("U".equals(var0)) {
            return 22;
        } else if ("I".equals(var0)) {
            return 23;
        } else if ("O".equals(var0)) {
            return 24;
        } else if ("P".equals(var0)) {
            return 25;
        } else if (Toolkit.getProperty("AWT.openBracket", "Open Bracket").equals(var0)) {
            return 26;
        } else if (Toolkit.getProperty("AWT.closeBracket", "Close Bracket").equals(var0)) {
            return 27;
        } else if (Toolkit.getProperty("AWT.enter", "Enter").equals(var0)) {
            return 28;
        } else if (Toolkit.getProperty("AWT.control", "Control").equals(var0)) {
            return 29;
        } else if ("A".equals(var0)) {
            return 30;
        } else if ("S".equals(var0)) {
            return 31;
        } else if ("D".equals(var0)) {
            return 32;
        } else if ("F".equals(var0)) {
            return 33;
        } else if ("G".equals(var0)) {
            return 34;
        } else if ("H".equals(var0)) {
            return 35;
        } else if ("J".equals(var0)) {
            return 36;
        } else if ("K".equals(var0)) {
            return 37;
        } else if ("L".equals(var0)) {
            return 38;
        } else if (Toolkit.getProperty("AWT.semicolon", "Semicolon").equals(var0)) {
            return 39;
        } else if (Toolkit.getProperty("AWT.quote", "Quote").equals(var0)) {
            return 40;
        } else if (Toolkit.getProperty("AWT.backQuote", "Back Quote").equals(var0)) {
            return 41;
        } else if (Toolkit.getProperty("AWT.shift", "Shift").equals(var0)) {
            return 42;
        } else if (Toolkit.getProperty("AWT.backSlash", "Back Slash").equals(var0)) {
            return 43;
        } else if ("Z".equals(var0)) {
            return 44;
        } else if ("X".equals(var0)) {
            return 45;
        } else if ("C".equals(var0)) {
            return 46;
        } else if ("V".equals(var0)) {
            return 47;
        } else if ("B".equals(var0)) {
            return 48;
        } else if ("N".equals(var0)) {
            return 49;
        } else if ("M".equals(var0)) {
            return 50;
        } else if (Toolkit.getProperty("AWT.comma", "Comma").equals(var0)) {
            return 51;
        } else if (Toolkit.getProperty("AWT.period", "Period").equals(var0)) {
            return 52;
        } else if (Toolkit.getProperty("AWT.slash", "Slash").equals(var0)) {
            return 53;
        } else if (Toolkit.getProperty("AWT.alt", "Alt").equals(var0)) {
            return 56;
        } else if (Toolkit.getProperty("AWT.space", "Space").equals(var0)) {
            return 57;
        } else if (Toolkit.getProperty("AWT.capsLock", "Caps Lock").equals(var0)) {
            return 58;
        } else if (Toolkit.getProperty("AWT.f1", "F1").equals(var0)) {
            return 59;
        } else if (Toolkit.getProperty("AWT.f2", "F2").equals(var0)) {
            return 60;
        } else if (Toolkit.getProperty("AWT.f3", "F3").equals(var0)) {
            return 61;
        } else if (Toolkit.getProperty("AWT.f4", "F4").equals(var0)) {
            return 62;
        } else if (Toolkit.getProperty("AWT.f5", "F5").equals(var0)) {
            return 63;
        } else if (Toolkit.getProperty("AWT.f6", "F6").equals(var0)) {
            return 64;
        } else if (Toolkit.getProperty("AWT.f7", "F7").equals(var0)) {
            return 65;
        } else if (Toolkit.getProperty("AWT.f8", "F8").equals(var0)) {
            return 66;
        } else if (Toolkit.getProperty("AWT.f9", "F9").equals(var0)) {
            return 67;
        } else if (Toolkit.getProperty("AWT.f10", "F10").equals(var0)) {
            return 68;
        } else if (Toolkit.getProperty("AWT.numLock", "Num Lock").equals(var0)) {
            return 69;
        } else if (Toolkit.getProperty("AWT.scrollLock", "Scroll Lock").equals(var0)) {
            return 70;
        } else if (Toolkit.getProperty("AWT.separator", "NumPad ,").equals(var0)) {
            return 83;
        } else if (Toolkit.getProperty("AWT.f11", "F11").equals(var0)) {
            return 87;
        } else if (Toolkit.getProperty("AWT.f12", "F12").equals(var0)) {
            return 88;
        } else if (Toolkit.getProperty("AWT.f13", "F13").equals(var0)) {
            return 91;
        } else if (Toolkit.getProperty("AWT.f14", "F14").equals(var0)) {
            return 92;
        } else if (Toolkit.getProperty("AWT.f15", "F15").equals(var0)) {
            return 93;
        } else if (Toolkit.getProperty("AWT.f16", "F16").equals(var0)) {
            return 99;
        } else if (Toolkit.getProperty("AWT.f17", "F17").equals(var0)) {
            return 100;
        } else if (Toolkit.getProperty("AWT.f18", "F18").equals(var0)) {
            return 101;
        } else if (Toolkit.getProperty("AWT.f19", "F19").equals(var0)) {
            return 102;
        } else if (Toolkit.getProperty("AWT.f20", "F20").equals(var0)) {
            return 103;
        } else if (Toolkit.getProperty("AWT.f21", "F21").equals(var0)) {
            return 104;
        } else if (Toolkit.getProperty("AWT.f22", "F22").equals(var0)) {
            return 105;
        } else if (Toolkit.getProperty("AWT.f23", "F23").equals(var0)) {
            return 106;
        } else if (Toolkit.getProperty("AWT.f24", "F24").equals(var0)) {
            return 107;
        } else if (Toolkit.getProperty("AWT.katakana", "Katakana").equals(var0)) {
            return 112;
        } else if (Toolkit.getProperty("AWT.underscore", "Underscore").equals(var0)) {
            return 115;
        } else if (Toolkit.getProperty("AWT.furigana", "Furigana").equals(var0)) {
            return 119;
        } else if (Toolkit.getProperty("AWT.kanji", "Kanji").equals(var0)) {
            return 121;
        } else if (Toolkit.getProperty("AWT.hiragana", "Hiragana").equals(var0)) {
            return 123;
        } else if (Toolkit.getProperty("AWT.yen", Character.toString('¥')).equals(var0)) {
            return 125;
        } else if (Toolkit.getProperty("AWT.printScreen", "Print Screen").equals(var0)) {
            return 3639;
        } else if (Toolkit.getProperty("AWT.pause", "Pause").equals(var0)) {
            return 3653;
        } else if (Toolkit.getProperty("AWT.home", "Home").equals(var0)) {
            return 3655;
        } else if (Toolkit.getProperty("AWT.pgup", "Page Up").equals(var0)) {
            return 3657;
        } else if (Toolkit.getProperty("AWT.end", "End").equals(var0)) {
            return 3663;
        } else if (Toolkit.getProperty("AWT.pgdn", "Page Down").equals(var0)) {
            return 3665;
        } else if (Toolkit.getProperty("AWT.insert", "Insert").equals(var0)) {
            return 3666;
        } else if (Toolkit.getProperty("AWT.delete", "Delete").equals(var0)) {
            return 3667;
        } else if (Toolkit.getProperty("AWT.meta", "Meta").equals(var0)) {
            return 3675;
        } else if (Toolkit.getProperty("AWT.context", "Context Menu").equals(var0)) {
            return 3677;
        } else if (Toolkit.getProperty("AWT.previous", "Previous").equals(var0)) {
            return 57360;
        } else if (Toolkit.getProperty("AWT.next", "Next").equals(var0)) {
            return 57369;
        } else if (Toolkit.getProperty("AWT.mute", "Mute").equals(var0)) {
            return 57376;
        } else if (Toolkit.getProperty("AWT.app_calculator", "App Calculator").equals(var0)) {
            return 57377;
        } else if (Toolkit.getProperty("AWT.play", "Play").equals(var0)) {
            return 57378;
        } else if (Toolkit.getProperty("AWT.stop", "Stop").equals(var0)) {
            return 57380;
        } else if (Toolkit.getProperty("AWT.eject", "Eject").equals(var0)) {
            return 57388;
        } else if (Toolkit.getProperty("AWT.voldn", "Volume Down").equals(var0)) {
            return 57390;
        } else if (Toolkit.getProperty("AWT.volup", "Volume Up").equals(var0)) {
            return 57392;
        } else if (Toolkit.getProperty("AWT.homepage", "Browser Home").equals(var0)) {
            return 57394;
        } else if (Toolkit.getProperty("AWT.app_music", "App Music").equals(var0)) {
            return 57404;
        } else if (Toolkit.getProperty("AWT.up", "Up").equals(var0)) {
            return 57416;
        } else if (Toolkit.getProperty("AWT.left", "Left").equals(var0)) {
            return 57419;
        } else if (Toolkit.getProperty("AWT.clear", "Clear").equals(var0)) {
            return 57420;
        } else if (Toolkit.getProperty("AWT.right", "Right").equals(var0)) {
            return 57421;
        } else if (Toolkit.getProperty("AWT.down", "Down").equals(var0)) {
            return 57424;
        } else if (Toolkit.getProperty("AWT.power", "Power").equals(var0)) {
            return 57438;
        } else if (Toolkit.getProperty("AWT.sleep", "Sleep").equals(var0)) {
            return 57439;
        } else if (Toolkit.getProperty("AWT.wake", "Wake").equals(var0)) {
            return 57443;
        } else if (Toolkit.getProperty("AWT.app_pictures", "App Pictures").equals(var0)) {
            return 57444;
        } else if (Toolkit.getProperty("AWT.search", "Browser Search").equals(var0)) {
            return 57445;
        } else if (Toolkit.getProperty("AWT.favorites", "Browser Favorites").equals(var0)) {
            return 57446;
        } else if (Toolkit.getProperty("AWT.refresh", "Browser Refresh").equals(var0)) {
            return 57447;
        } else if (Toolkit.getProperty("AWT.stop", "Browser Stop").equals(var0)) {
            return 57448;
        } else if (Toolkit.getProperty("AWT.forward", "Browser Forward").equals(var0)) {
            return 57449;
        } else if (Toolkit.getProperty("AWT.back", "Browser Back").equals(var0)) {
            return 57450;
        } else if (Toolkit.getProperty("AWT.app_mail", "App Mail").equals(var0)) {
            return 57452;
        } else if (Toolkit.getProperty("AWT.select", "Select").equals(var0)) {
            return 57453;
        } else if (Toolkit.getProperty("AWT.sun_open", "Sun Open").equals(var0)) {
            return 65396;
        } else if (Toolkit.getProperty("AWT.sun_help", "Sun Help").equals(var0)) {
            return 65397;
        } else if (Toolkit.getProperty("AWT.sun_props", "Sun Props").equals(var0)) {
            return 65398;
        } else if (Toolkit.getProperty("AWT.sun_front", "Sun Front").equals(var0)) {
            return 65399;
        } else if (Toolkit.getProperty("AWT.sun_stop", "Sun Stop").equals(var0)) {
            return 65400;
        } else if (Toolkit.getProperty("AWT.sun_again", "Sun Again").equals(var0)) {
            return 65401;
        } else if (Toolkit.getProperty("AWT.sun_cut", "Sun Cut").equals(var0)) {
            return 65403;
        } else if (Toolkit.getProperty("AWT.sun_copy", "Sun Copy").equals(var0)) {
            return 65404;
        } else if (Toolkit.getProperty("AWT.sun_insert", "Sun Insert").equals(var0)) {
            return 65405;
        } else if (Toolkit.getProperty("AWT.sun_find", "Sun Find").equals(var0)) {
            return 65406;
        }
        return -1;
    }

}
