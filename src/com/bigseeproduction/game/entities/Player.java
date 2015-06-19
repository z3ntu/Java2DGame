package com.bigseeproduction.game.entities;

import com.bigseeproduction.game.Game;
import com.bigseeproduction.game.GameState;
import com.bigseeproduction.game.gfx.Colors;
import com.bigseeproduction.game.gfx.Screen;
import com.bigseeproduction.game.level.Level;
import com.bigseeproduction.game.level.LevelsHelper;
import com.bigseeproduction.game.listener.InputHandler;
import com.bigseeproduction.game.menus.MainMenu;
import com.bigseeproduction.game.menus.OptionMenu;
import com.bigseeproduction.game.menus.PauseMenu;
import com.bigseeproduction.game.sound.Music;
import com.bigseeproduction.game.sound.Sound;

/**
 * Created by Luca on 07.04.2015.
 */
public class Player extends Mob {

    protected boolean isSwimming = false;
    private InputHandler input;
    private int color = 0;
    private int tickCount = 0;
    private int textureTopLeftX;
    private int textureTopLeftY;
    private Game game;


    public Player(Game game, Level level, int x, int y, InputHandler input, int textureTopLeftX, int textureTopLeftY, int color) {
        super(level, "Player", x, y, 1, 1);
        this.input = input;
        scale = 1;
        this.textureTopLeftX = textureTopLeftX;
        this.textureTopLeftY = textureTopLeftY;
        this.color = color;
        this.game = game;
    }


    @Override
    public void tick() {
        int xa = 0;
        int ya = 0;

        /**
         * MAIN MENU HANDLING
         */
        if (Game.gameState == GameState.MAINMENU) {
            if (input.up.isPressed() && input.up.hasChanged()) {
                Sound.SELECT.play();
                MainMenu.currentChoice--;
                if (MainMenu.currentChoice < 0)
                    MainMenu.currentChoice = MainMenu.texts.length - 1;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Up Key");
            }
            if (input.down.isPressed() && input.down.hasChanged() || MainMenu.currentChoice > MainMenu.texts.length) {
                Sound.SELECT.play();
                MainMenu.currentChoice++;
                if (MainMenu.currentChoice >= MainMenu.texts.length)
                    MainMenu.currentChoice = 0;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Down Key");
            }
            if (input.enter.isPressed() && input.enter.hasChanged()) {
                Sound.SELECT.play();
                switch (MainMenu.currentChoice) {
                    /**
                     * RUN
                     */
                    case 0:
                        Game.gameState = GameState.RUNNING;
                        break;
                    /**
                     * SELECT LEVEL
                     */
                    case 1:
                        Game.gameState = GameState.SELECTLEVEL;
                        MainMenu.currentChoice = 0;
                        break;
                    case 2:
                        OptionMenu.preOptionGameState = Game.gameState;
                        Game.gameState = GameState.OPTIONMENU;
                        MainMenu.currentChoice = 0;
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
            }
            if (input.pause.isPressed() && input.pause.hasChanged()) {
                Sound.SELECT.play();
                //Game.gameState = GameState.RUNNING;
                Game.LOGGER.log(java.util.logging.Level.FINER, "MainMenu Key");
            }
        }

        /**
         * SELECTLEVEL HANDLING
         */
        //TODO: replace LevelsHelper.getLevelsNamesAsArray().length with levelCountPlusOne!!
        else if (Game.gameState == GameState.SELECTLEVEL) {
            int levelCountPlusOne = LevelsHelper.getLevelsNamesAsArray().length;
            if (input.up.isPressed() && input.up.hasChanged()) {
                Sound.SELECT.play();
                MainMenu.currentChoice--;
                if (MainMenu.currentChoice < 0)
                    MainMenu.currentChoice = levelCountPlusOne - 1;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Up Key");
            }
            if (input.down.isPressed() && input.down.hasChanged()) {
                Sound.SELECT.play();
                MainMenu.currentChoice++;
                if (MainMenu.currentChoice >= levelCountPlusOne /** maybe??????*/ || MainMenu.currentChoice > 8)
                    MainMenu.currentChoice = 0;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Down Key");
            }
            if (input.enter.isPressed() && input.enter.hasChanged()) {
                Sound.SELECT.play();
                switch (MainMenu.currentChoice) {
                    case 0:
                        Game.gameState = GameState.MAINMENU;
                        MainMenu.currentChoice = 0;
                        break;
                    default:
                        LevelsHelper.handleLoadLevels(game, MainMenu.currentChoice);
                        break;
                }
            }
            if (input.pause.isPressed() && input.pause.hasChanged()) {
                Sound.SELECT.play();
                Game.gameState = GameState.MAINMENU;
                Game.LOGGER.log(java.util.logging.Level.FINER, "MainMenu Key");
            }
        }

        /**
         * GAME HANDLING
         */
        else if (Game.gameState == GameState.RUNNING) {
            if (input.up.isPressed()) {
                ya--;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Up Key");
            }
            if (input.down.isPressed()) {
                ya++;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Down Key");
            }
            if (input.left.isPressed()) {
                xa--;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Left Key");
            }
            if (input.right.isPressed()) {
                xa++;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Right Key");
            }
            if (input.debug.isPressed() && input.debug.hasChanged()) {
                Game.debugScreen = !Game.debugScreen;
                Game.LOGGER.log(java.util.logging.Level.FINER, "Debug Key");
            }
            if (input.pause.isPressed() && input.pause.hasChanged()) {
                Sound.SELECT.play();
                Game.gameState = GameState.PAUSEMENU;
                Game.LOGGER.log(java.util.logging.Level.FINER, "PauseMenu Key");
            }
        }
        /**
         * PAUSE MENU HANDLING
         */
        else if (Game.gameState == GameState.PAUSEMENU) {
            if (input.up.isPressed() && input.up.hasChanged()) {
                Sound.SELECT.play();
                PauseMenu.currentChoice--;
                if (PauseMenu.currentChoice < 0)
                    PauseMenu.currentChoice = PauseMenu.texts.length - 1;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Up Key");
            }
            if (input.down.isPressed() && input.down.hasChanged()) {
                Sound.SELECT.play();
                PauseMenu.currentChoice++;
                if (PauseMenu.currentChoice >= PauseMenu.texts.length)
                    PauseMenu.currentChoice = 0;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Down Key");
            }
            if (input.enter.isPressed() && input.enter.hasChanged()) {
                Sound.SELECT.play();
                switch (PauseMenu.currentChoice) {
                    /**
                     * RESUME
                     */
                    case 0:
                        Game.gameState = GameState.RUNNING;
                        PauseMenu.currentChoice = 0;
                        break;
                    case 1:
                        OptionMenu.preOptionGameState = Game.gameState;
                        Game.gameState = GameState.OPTIONMENU;
                        PauseMenu.currentChoice = 0;
                        break;
                    case 2:
                        Game.gameState = GameState.MAINMENU;
                        break;
                }
            }
            if (input.pause.isPressed() && input.pause.hasChanged()) {
                Sound.SELECT.play();
                Game.gameState = GameState.RUNNING;
                Game.LOGGER.log(java.util.logging.Level.FINER, "PauseMenu Key");
            }
        }
        /**
         * OPTION MENU HANDLING
         */
        else if (Game.gameState == GameState.OPTIONMENU) {

            if (input.up.isPressed() && input.up.hasChanged()) {
                Sound.SELECT.play();
                OptionMenu.currentChoice--;
                if (OptionMenu.currentChoice < 0)
                    OptionMenu.currentChoice = OptionMenu.texts.length - 1;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Up Key");
            }
            if (input.down.isPressed() && input.down.hasChanged()) {
                Sound.SELECT.play();
                OptionMenu.currentChoice++;
                if (OptionMenu.currentChoice >= OptionMenu.texts.length)
                    OptionMenu.currentChoice = 0;
                Game.LOGGER.log(java.util.logging.Level.FINEST, "Down Key");
            }
            if (input.enter.isPressed() && input.enter.hasChanged()) {
                Sound.SELECT.play();

                switch (OptionMenu.currentChoice) {
                    case 0:
                        Game.playSounds = !Game.playSounds;
                        Game.LOGGER.log(java.util.logging.Level.FINER, "toggled sounds");
                        break;
                    case 1:
                        Game.playMusic = !Game.playMusic;
                        if (Game.playMusic)
                            Music.MAIN.play();
                        else
                            Music.MAIN.stop();
                        Game.LOGGER.log(java.util.logging.Level.FINER, "toggled music");
                        break;
                    case 2:
                        Game.gameState = OptionMenu.preOptionGameState;
                        OptionMenu.currentChoice = 0;
                        break;
                }
            }
            if (input.pause.isPressed() && input.pause.hasChanged()) {
                Sound.SELECT.play();
                Game.gameState = GameState.PAUSEMENU;
                Game.LOGGER.log(java.util.logging.Level.FINER, "OptionMenu Key");
            }
        }

        if (xa != 0 || ya != 0) {
            moving = move(xa, ya);
        } else {
            moving = false;
        }
        if (level.getTile(this.x >> 3, (this.y + 4) >> 3).getID() == 3) {
            isSwimming = true;
        }
        if (isSwimming && level.getTile(this.x >> 3, (this.y + 4) >> 3).getID() != 3) {
            isSwimming = false;
        }

        tickCount++;
    }

    @Override
    public void render(Screen screen) {
        int xTile = textureTopLeftX;
        int yTile = textureTopLeftY;
        int flipTop = 0;
        int flipBottom = 0;
        int walkingAnimationSpeed = 4;
        if (movingDir == 0 || movingDir == 1) {
            if (moving) {
                flipTop = (numSteps >> walkingAnimationSpeed) & 1;
                flipBottom = (numSteps >> walkingAnimationSpeed) & 1;
            } else {
                //System.out.println("not moving!");
            }
        }

        if (movingDir == 1) {
            xTile += 2;
        } else if (movingDir > 1) {
            if (!moving) xTile = 4;
            else xTile += 4 + ((numSteps >> walkingAnimationSpeed) & 1) * 2;
            flipTop = (movingDir - 1) % 2;
        }
        /*
        if (movingDir == 1) {
            xTile += 2;
        } else {
            xTile += 2;

        }
*/

        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;

        if (isSwimming) {
            int waterColor;
            yOffset += 4;
            if (tickCount % 60 < 15) {
                waterColor = Colors.getColor(-1, -1, 225, -1);
            } else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
                yOffset -= 1;

                waterColor = Colors.getColor(-1, 225, 115, -1);
            } else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
                waterColor = Colors.getColor(-1, 115, -1, 225);
            } else {
                waterColor = Colors.getColor(-1, 225, 115, -1);
                yOffset -= 1;

            }
            screen.render(xOffset, yOffset + 3, 27 * 32, waterColor, 0x00, 1);
            screen.render(xOffset + 8, yOffset + 3, 27 * 32, waterColor, 0x01, 1);
        }
        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);

        if (!isSwimming) {
            screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
            screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
        }
    }

    @Override
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = -1;
        int yMax = 7;
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }

        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }

        return false;
    }
}
