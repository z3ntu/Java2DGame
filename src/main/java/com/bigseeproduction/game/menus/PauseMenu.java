package com.bigseeproduction.game.menus;

import com.bigseeproduction.game.Game;

import java.awt.*;

/**
 * Created by Luca on 12.04.2015.
 */
public class PauseMenu extends Menu {

    public static final String[] texts = {"Resume", "Options", "Main Menu"};

    public static int currentChoice = 0;

    public PauseMenu(Game game) {
        super(game);
    }

    @Override
    public void drawMenu(Graphics g) {
        dimm(g);
        printPausedText(g);
        drawTextArray(g, texts, yCoords, new Font("Arial", Font.BOLD, 40), Color.WHITE);
        drawArrow(g, yCoords, currentChoice, arrowImg, texts.length);
    }


}
