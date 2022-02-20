package net.breakfaststudios.util;

import java.awt.*;

public class Converter {

    public static String getKeyText(int var0) {
        return switch (var0) {
            case 0 -> Toolkit.getProperty("AWT.undefined", "Undefined");
            case 1 -> Toolkit.getProperty("AWT.escape", "Escape");
            case 2 -> "1";
            case 3 -> "2";
            case 4 -> "3";
            case 5 -> "4";
            case 6 -> "5";
            case 7 -> "6";
            case 8 -> "7";
            case 9 -> "8";
            case 10 -> "9";
            case 11 -> "0";
            case 12 -> Toolkit.getProperty("AWT.minus", "Minus");
            case 13 -> Toolkit.getProperty("AWT.equals", "Equals");
            case 14 -> Toolkit.getProperty("AWT.backSpace", "Backspace");
            case 15 -> Toolkit.getProperty("AWT.tab", "Tab");
            case 16 -> "Q";
            case 17 -> "W";
            case 18 -> "E";
            case 19 -> "R";
            case 20 -> "T";
            case 21 -> "Y";
            case 22 -> "U";
            case 23 -> "I";
            case 24 -> "O";
            case 25 -> "P";
            case 26 -> Toolkit.getProperty("AWT.openBracket", "Open Bracket");
            case 27 -> Toolkit.getProperty("AWT.closeBracket", "Close Bracket");
            case 28 -> Toolkit.getProperty("AWT.enter", "Enter");
            case 29 -> Toolkit.getProperty("AWT.control", "Control");
            case 30 -> "A";
            case 31 -> "S";
            case 32 -> "D";
            case 33 -> "F";
            case 34 -> "G";
            case 35 -> "H";
            case 36 -> "J";
            case 37 -> "K";
            case 38 -> "L";
            case 39 -> Toolkit.getProperty("AWT.semicolon", "Semicolon");
            case 40 -> Toolkit.getProperty("AWT.quote", "Quote");
            case 41 -> Toolkit.getProperty("AWT.backQuote", "Back Quote");
            case 42 -> Toolkit.getProperty("AWT.shift", "Shift");
            case 43 -> Toolkit.getProperty("AWT.backSlash", "Back Slash");
            case 44 -> "Z";
            case 45 -> "X";
            case 46 -> "C";
            case 47 -> "V";
            case 48 -> "B";
            case 49 -> "N";
            case 50 -> "M";
            case 51 -> Toolkit.getProperty("AWT.comma", "Comma");
            case 52 -> Toolkit.getProperty("AWT.period", "Period");
            case 53 -> Toolkit.getProperty("AWT.slash", "Slash");
            case 54 -> "RShift";
            case 56 -> Toolkit.getProperty("AWT.alt", "Alt");
            case 57 -> Toolkit.getProperty("AWT.space", "Space");
            case 58 -> Toolkit.getProperty("AWT.capsLock", "Caps Lock");
            case 59 -> Toolkit.getProperty("AWT.f1", "F1");
            case 60 -> Toolkit.getProperty("AWT.f2", "F2");
            case 61 -> Toolkit.getProperty("AWT.f3", "F3");
            case 62 -> Toolkit.getProperty("AWT.f4", "F4");
            case 63 -> Toolkit.getProperty("AWT.f5", "F5");
            case 64 -> Toolkit.getProperty("AWT.f6", "F6");
            case 65 -> Toolkit.getProperty("AWT.f7", "F7");
            case 66 -> Toolkit.getProperty("AWT.f8", "F8");
            case 67 -> Toolkit.getProperty("AWT.f9", "F9");
            case 68 -> Toolkit.getProperty("AWT.f10", "F10");
            case 69 -> Toolkit.getProperty("AWT.numLock", "Num Lock");
            case 70 -> Toolkit.getProperty("AWT.scrollLock", "Scroll Lock");
            case 83 -> Toolkit.getProperty("AWT.separator", "NumPad ,");
            case 87 -> Toolkit.getProperty("AWT.f11", "F11");
            case 88 -> Toolkit.getProperty("AWT.f12", "F12");
            case 91 -> Toolkit.getProperty("AWT.f13", "F13");
            case 92 -> Toolkit.getProperty("AWT.f14", "F14");
            case 93 -> Toolkit.getProperty("AWT.f15", "F15");
            case 99 -> Toolkit.getProperty("AWT.f16", "F16");
            case 100 -> Toolkit.getProperty("AWT.f17", "F17");
            case 101 -> Toolkit.getProperty("AWT.f18", "F18");
            case 102 -> Toolkit.getProperty("AWT.f19", "F19");
            case 103 -> Toolkit.getProperty("AWT.f20", "F20");
            case 104 -> Toolkit.getProperty("AWT.f21", "F21");
            case 105 -> Toolkit.getProperty("AWT.f22", "F22");
            case 106 -> Toolkit.getProperty("AWT.f23", "F23");
            case 107 -> Toolkit.getProperty("AWT.f24", "F24");
            case 112 -> Toolkit.getProperty("AWT.katakana", "Katakana");
            case 115 -> Toolkit.getProperty("AWT.underscore", "Underscore");
            case 119 -> Toolkit.getProperty("AWT.furigana", "Furigana");
            case 121 -> Toolkit.getProperty("AWT.kanji", "Kanji");
            case 123 -> Toolkit.getProperty("AWT.hiragana", "Hiragana");
            case 125 -> Toolkit.getProperty("AWT.yen", Character.toString('¥'));
            case 3639 -> Toolkit.getProperty("AWT.printScreen", "Print Screen");
            case 3653 -> Toolkit.getProperty("AWT.pause", "Pause");
            case 3655 -> Toolkit.getProperty("AWT.home", "Home");
            case 3657 -> Toolkit.getProperty("AWT.pgup", "Page Up");
            case 3663 -> Toolkit.getProperty("AWT.end", "End");
            case 3665 -> Toolkit.getProperty("AWT.pgdn", "Page Down");
            case 3666 -> Toolkit.getProperty("AWT.insert", "Insert");
            case 3667 -> Toolkit.getProperty("AWT.delete", "Delete");
            case 3675 -> Toolkit.getProperty("AWT.meta", "Meta");
            case 3677 -> Toolkit.getProperty("AWT.context", "Context Menu");
            case 57360 -> Toolkit.getProperty("AWT.previous", "Previous");
            case 57369 -> Toolkit.getProperty("AWT.next", "Next");
            case 57376 -> Toolkit.getProperty("AWT.mute", "Mute");
            case 57377 -> Toolkit.getProperty("AWT.app_calculator", "App Calculator");
            case 57378 -> Toolkit.getProperty("AWT.play", "Play");
            case 57380 -> Toolkit.getProperty("AWT.stop", "Stop");
            case 57388 -> Toolkit.getProperty("AWT.eject", "Eject");
            case 57390 -> Toolkit.getProperty("AWT.voldn", "Volume Down");
            case 57392 -> Toolkit.getProperty("AWT.volup", "Volume Up");
            case 57394 -> Toolkit.getProperty("AWT.homepage", "Browser Home");
            case 57404 -> Toolkit.getProperty("AWT.app_music", "App Music");
            case 57416 -> Toolkit.getProperty("AWT.up", "Up");
            case 57419 -> Toolkit.getProperty("AWT.left", "Left");
            case 57420 -> Toolkit.getProperty("AWT.clear", "Clear");
            case 57421 -> Toolkit.getProperty("AWT.right", "Right");
            case 57424 -> Toolkit.getProperty("AWT.down", "Down");
            case 57438 -> Toolkit.getProperty("AWT.power", "Power");
            case 57439 -> Toolkit.getProperty("AWT.sleep", "Sleep");
            case 57443 -> Toolkit.getProperty("AWT.wake", "Wake");
            case 57444 -> Toolkit.getProperty("AWT.app_pictures", "App Pictures");
            case 57445 -> Toolkit.getProperty("AWT.search", "Browser Search");
            case 57446 -> Toolkit.getProperty("AWT.favorites", "Browser Favorites");
            case 57447 -> Toolkit.getProperty("AWT.refresh", "Browser Refresh");
            case 57448 -> Toolkit.getProperty("AWT.stop", "Browser Stop");
            case 57449 -> Toolkit.getProperty("AWT.forward", "Browser Forward");
            case 57450 -> Toolkit.getProperty("AWT.back", "Browser Back");
            case 57452 -> Toolkit.getProperty("AWT.app_mail", "App Mail");
            case 57453 -> Toolkit.getProperty("AWT.select", "Select");
            case 65396 -> Toolkit.getProperty("AWT.sun_open", "Sun Open");
            case 65397 -> Toolkit.getProperty("AWT.sun_help", "Sun Help");
            case 65398 -> Toolkit.getProperty("AWT.sun_props", "Sun Props");
            case 65399 -> Toolkit.getProperty("AWT.sun_front", "Sun Front");
            case 65400 -> Toolkit.getProperty("AWT.sun_stop", "Sun Stop");
            case 65401 -> Toolkit.getProperty("AWT.sun_again", "Sun Again");
            case 65403 -> Toolkit.getProperty("AWT.sun_cut", "Sun Cut");
            case 65404 -> Toolkit.getProperty("AWT.sun_copy", "Sun Copy");
            case 65405 -> Toolkit.getProperty("AWT.sun_insert", "Sun Insert");
            case 65406 -> Toolkit.getProperty("AWT.sun_find", "Sun Find");
            case 1076 -> "num5";
            case 1074 -> "num-";
            case 1078 -> "num+";
            case 1055 -> "num*";
            case 1082 -> "num0";
            case 1071 -> "num7";
            case 1073 -> "num9";
            case 1083 -> "num.";
            case 1079 -> "num1";
            case 1081 -> "num3";
            case 1072 -> "num8";
            case 1080 -> "num2";
            case 1077 -> "num6";
            case 1075 -> "num4";

            default -> Toolkit.getProperty("AWT.unknown", "Unknown") + " keyCode: 0x" + Integer.toString(var0, 16);
        };
    }

    /**
     * Converts keycodes into the String of the key.
     *
     * @param var0 The character you need the code of as a string
     * @return key code int
     */
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
        } else if ("num5".equals(var0)) {
            return 1076;
        } else if ("num-".equals(var0)) {
            return 1074;
        } else if ("num+".equals(var0)) {
            return 1078;
        } else if ("num*".equals(var0)) {
            return 1055;
        } else if ("num0".equals(var0)) {
            return 1082;
        } else if ("num7".equals(var0)) {
            return 1071;
        } else if ("num9".equals(var0)) {
            return 1073;
        } else if ("num.".equals(var0)) {
            return 1083;
        } else if ("num1".equals(var0)) {
            return 1079;
        } else if ("num3".equals(var0)) {
            return 1081;
        } else if ("num8".equals(var0)) {
            return 1072;
        } else if ("num2".equals(var0)) {
            return 1080;
        } else if ("num6".equals(var0)) {
            return 1077;
        } else if ("num4".equals(var0)) {
            return 1075;
        }
        return -1;
    }

}
