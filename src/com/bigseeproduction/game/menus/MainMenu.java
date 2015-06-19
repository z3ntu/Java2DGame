package com.bigseeproduction.game.menus;

import com.bigseeproduction.game.Game;
import com.bigseeproduction.game.level.LevelsHelper;

import java.awt.*;

/**
 * Created by Luca on 12.04.2015.
 */
public class MainMenu extends Menu {

    public static final String[] texts = {"Run", "Select Level", "Options", "Exit", "test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10", "test11", "test12"};

    public MainMenu(Game game) {
        super(game);
        System.out.println(texts.length);
    }

    @Override
    public void drawMenu(Graphics g) {
        dimm(g);
        printText(g, "Main Menu", 5, 50, new Font("Arial", Font.BOLD, 50), Color.WHITE);
        drawTextArray(g, texts, yCoords, new Font("Arial", Font.BOLD, 40), Color.WHITE);
        drawArrow(g, yCoords, currentChoice, arrowImg, texts.length);
    }

    public void drawLevels(Graphics g) {
        dimm(g);
        printText(g, "Load level", 5, 50, new Font("Arial", Font.BOLD, 50), Color.WHITE);
        drawTextArray(g, LevelsHelper.getLevelsNamesAsArray(), yCoords, new Font("Arial", Font.BOLD, 40), Color.WHITE);
        drawArrow(g, yCoords, currentChoice, arrowImg, texts.length);
    }
}
