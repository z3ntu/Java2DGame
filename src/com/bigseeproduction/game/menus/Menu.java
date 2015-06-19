package com.bigseeproduction.game.menus;

import com.bigseeproduction.game.Game;
import com.bigseeproduction.game.gfx.Colors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Luca on 12.04.2015.
 */
public abstract class Menu {

    public static final int textColor = Colors.getColor(-1, -1, -1, 555);
    public static final int[] yCoords = {150, 200, 250, 300, 350, 400, 450, 500, 550, 600};

    public static final int[] moreCoords = {100, 650};
    public static int currentChoice = 0;
    protected static BufferedImage arrowImg;
    protected Game game;

    public Menu(Game game) {
        this.game = game;
        try {
            arrowImg = ImageIO.read(Game.class.getResource("/images/arrow.png"));
        } catch (IOException e) {
            Game.LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    public abstract void drawMenu(Graphics g);

    protected void dimm(Graphics g) {
        g.drawImage(game.dimmImage, 0, 0, game.getWidth(), game.getHeight(), null);
    }

    protected void printText(Graphics g, String msg, int x, int y, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(msg, x, y);
    }

    protected void printPausedText(Graphics g) {
        printText(g, "PAUSED", 5, 50, new Font("Arial", Font.BOLD, 50), Color.WHITE);
    }

    protected void drawTextArray(Graphics g, String[] textsToPrint, int[] yCoords, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);


        int start = 0;

        if (textsToPrint.length > 9) {

            if (currentChoice > 4 && !(textsToPrint.length - currentChoice < 4)) {
                //middle
//                System.out.println("should scroll");
//                System.out.println("stay in middle");
                g.drawString("State: middle", 400, 500);

                start = currentChoice - 4;
            } else if (textsToPrint.length - currentChoice <= 4) {
//                System.out.println("bottom");
                start = textsToPrint.length - 8;
                //bottom
                g.drawString("State: bottom", 400, 500);

            } else {
//                System.out.println("top");
                //top
//                System.out.println("not scrolling");
                start = 0;
                g.drawString("State: top", 400, 500);
            }

        }
        int count = 0;
        for (int i = start; i < textsToPrint.length && i < start + 9; i++) {
            g.drawString(textsToPrint[i], 100, yCoords[count++]);
        }
        g.drawString("Actual: " + start, 400, 400);
    }

    protected void drawArrow(Graphics g, int yCoords[], int currentChoice, BufferedImage arrowImage, int total) {
        /**
         * LET THE ARROW STAY IN THE MIDDLE IF OVER 9 ENTRIES AND NOT LESS THAN 4 REMAINING
         */
        int start = 0;

        g.drawString("CurrentChoice: " + currentChoice, 400, 100);
        g.drawString("Total: " + total, 400, 200);

        if (total > 9) {
            /**
             * 0, 1, 2, 3 ==    top
             *          4 ==    middle
             * 5, 6, 7, 8 ==    bottom
             */
            // 4 == middle
            if (currentChoice > 4 && !(total - currentChoice < 4)) {
//                System.out.println("stay in middle");
                //stay in the middle
//                System.out.println(4);
                drawImage(g, 40, yCoords[4] - 35, arrowImage, arrowImage.getHeight() * 3, arrowImage.getWidth() * 3);
//            start = currentChoice - 4;
            } else if (total - currentChoice <= 4) {
//                System.out.println("bottom");
                //bottom
//                System.out.println(yCoords.length - 1 - (total - currentChoice));
                drawImage(g, 40, yCoords[yCoords.length - 2 - (total - currentChoice)] - 35, arrowImage, arrowImage.getHeight() * 3, arrowImage.getWidth() * 3);
            } else {
//                System.out.println("top");
                //top
//                System.out.println(currentChoice);
                drawImage(g, 40, yCoords[currentChoice] - 35, arrowImage, arrowImage.getHeight() * 3, arrowImage.getWidth() * 3);
            }
        } else {
            //if you dont have to care :)
            drawImage(g, 40, yCoords[currentChoice] - 35, arrowImage, arrowImage.getHeight() * 3, arrowImage.getWidth() * 3);
        }
    }

    protected void drawImage(Graphics g, int x, int y, BufferedImage image, int width, int height) {
//        g.drawString("Actual: " + y, 400, 300);
        g.drawImage(image, x, y, width, height, null);
    }

}
