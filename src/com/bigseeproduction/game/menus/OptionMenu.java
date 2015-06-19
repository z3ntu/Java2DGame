package com.bigseeproduction.game.menus;

import com.bigseeproduction.game.Game;
import com.bigseeproduction.game.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Luca on 12.04.2015.
 */
public class OptionMenu extends Menu {

    public static final String[] texts = {"Sounds?", "Music?", "Back"};
    public static GameState preOptionGameState;
    private BufferedImage checkbox;
    private BufferedImage tick;

    public OptionMenu(Game game) {
        super(game);
        try {
            checkbox = ImageIO.read(Game.class.getResource("/images/checkbox.png"));
            tick = ImageIO.read(Game.class.getResource("/images/tick.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawMenu(Graphics g) {
        dimm(g);
        printPausedText(g);
        drawTextArray(g, texts, yCoords, new Font("Arial", Font.BOLD, 40), Color.WHITE);
        drawArrow(g, yCoords, currentChoice, arrowImg, texts.length);

        int x = 280;

        drawImage(g, x, yCoords[0] - 35, checkbox, checkbox.getWidth(), checkbox.getHeight());
        drawImage(g, x, yCoords[1] - 35, checkbox, checkbox.getWidth(), checkbox.getHeight());

        if (Game.playSounds)
            drawImage(g, x, yCoords[0] - 35, tick, tick.getWidth(), tick.getHeight());
        if (Game.playMusic)
            drawImage(g, x, yCoords[1] - 35, tick, tick.getWidth(), tick.getHeight());

    }
}
