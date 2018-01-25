package com.bigseeproduction.game.gfx;

/**
 * Created by Luca on 07.04.2015.
 */
public class Font {

    public static void render(String msg, Screen screen, int x, int y, int color, int scale) {
        msg = msg.toUpperCase();

        for (int i = 0; i < msg.length(); i++) {
            String chars = "" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " +
                    "0123456789.,:;'\"!?$%()-=+/      ";
            int charIndex = chars.indexOf(msg.charAt(i));
            if (charIndex >= 0) screen.render(x + (i * 8), y, charIndex + 30 * 32, color, 0x00, scale);
        }
    }

    public static void showMiddleText(String msg, Screen screen, int color, int scale) {
        render(msg, screen, screen.xOffset + screen.width / 2 - (msg.length() * 8) / 2, screen.yOffset + screen.height / 2, color, scale);
    }

    public static void showOverlay(String msg, Screen screen, int color, int scale, int x, int y) {
        render(msg, screen, x, y, color, scale);
    }
}
