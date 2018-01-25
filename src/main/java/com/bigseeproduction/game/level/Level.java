package com.bigseeproduction.game.level;

import com.bigseeproduction.game.Game;
import com.bigseeproduction.game.entities.Entity;
import com.bigseeproduction.game.gfx.Screen;
import com.bigseeproduction.game.level.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 07.04.2015.
 */
public class Level {

    public static int startX;
    public static int startY;
    public int width;
    public int height;
    private byte[] tiles;
    private List<Entity> entities = new ArrayList<Entity>();
    private String imagePath;
    private BufferedImage image;

    @Deprecated
    public Level(String imagePath, int startX, int startY) {
        if (imagePath != null) {
            this.imagePath = imagePath;
            this.loadLevelFromFile();
            this.startX = startX;
            this.startY = startY;

        } else {
            this.width = 64;
            this.height = 64;
            tiles = new byte[width * height];
            this.generateLevel();
        }
    }

    public Level(Levels level) {
        this(level.getFilePath(), level.getStartX(), level.getStartY());
    }

    private void loadLevelFromFile() {
        try {
            this.image = ImageIO.read(Level.class.getResource(this.imagePath));
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
            tiles = new byte[width * height];
            this.loadTiles();
            Game.LOGGER.log(java.util.logging.Level.INFO, "Level has been loaded!");
        } catch (IOException e) {
            Game.LOGGER.log(java.util.logging.Level.SEVERE, e.toString());
        }

    }

    private void loadTiles() {
        int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Tile t : Tile.tiles) {
                    if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
                        this.tiles[x + y * width] = t.getID();
                        break;
                    }
                }
            }
        }
    }

    private void saveLevelToFile() {
        try {
            ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
            Game.LOGGER.log(java.util.logging.Level.INFO, "Level has been saved!");
        } catch (IOException e) {
            Game.LOGGER.log(java.util.logging.Level.SEVERE, e.toString());
        }
    }

    public void alterTile(int x, int y, Tile newTile) {
        this.tiles[x + y * width] = newTile.getID();
        image.setRGB(x, y, newTile.getLevelColor());
    }

    public void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x * y % 10 < 5)
                    tiles[x + y * width] = Tile.GRASS.getID();
                else
                    tiles[x + y * width] = Tile.WATER.getID();
            }
        }
    }


    public synchronized List<Entity> getEntities() {
        return entities;
    }

    public void tick() {
        for (Entity e : getEntities()) {
            e.tick();
        }
        for (Tile t : Tile.tiles) {
            if (t == null) {
                break;
            }
            t.tick();
        }
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        if (xOffset < 0) xOffset = 0;
        if (xOffset > ((width << 3) - screen.width)) xOffset = ((width << 3) - screen.width);
        if (yOffset < 0) yOffset = 0;
        if (yOffset > ((height << 3) - screen.height)) yOffset = ((height << 3) - screen.height);

        screen.setOffset(xOffset, yOffset);

        for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
            for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
                getTile(x, y).render(screen, this, x << 3, y << 3);
            }
        }
    }

    public void renderEntities(Screen screen) {
        for (Entity e : entities) {
            e.render(screen);
        }
    }

    public Tile getTile(int x, int y) {
        if (0 > x || x >= width || 0 > y || y >= height) return Tile.VOID;
        return Tile.tiles[tiles[x + y * width]];
    }


    public void addEntity(Entity entity) {
        this.getEntities().add(entity);
        entity.x = startX;
        entity.y = startY;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void removeEntity(Entity entity) {
        this.getEntities().remove(entity);
    }
}
