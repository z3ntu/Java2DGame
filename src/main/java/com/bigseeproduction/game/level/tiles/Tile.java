package com.bigseeproduction.game.level.tiles;

import com.bigseeproduction.game.gfx.Colors;
import com.bigseeproduction.game.gfx.Screen;
import com.bigseeproduction.game.level.Level;

/**
 * Created by Luca on 07.04.2015.
 */
public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];

    public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.getColor(0, -1, -1, -1), 0xFF000000);
    public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.getColor(-1, 333, 333, -1), 0xFF555555);
    public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.getColor(-1, 131, 141, -1), 0xFF00FF00);
    public static final Tile SAND = new BasicTile(4, 3, 0, Colors.getColor(-1, 551, 552, -1), 0xFFFFFF00);
    public static final Tile WATER = new AnimatedTile(3, new int[][]{{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colors.getColor(-1, 004, 115, -1), 0xFF0000FF, 1000);

    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColor;

    public Tile(int id, boolean solid, boolean emitter, int levelColor) {
        this.id = (byte) id;
        if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
        this.solid = solid;
        this.emitter = emitter;
        this.levelColor = levelColor;
        tiles[id] = this;
    }

    public byte getID() {
        return id;
    }

    public boolean isSolid() {
        return solid;

    }

    public boolean isEmitter() {
        return emitter;
    }

    public int getLevelColor() {
        return levelColor;
    }

    public abstract void tick();

    public abstract void render(Screen screen, Level level, int x, int y);

}
