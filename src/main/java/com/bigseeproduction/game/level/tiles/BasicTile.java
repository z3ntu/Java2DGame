package com.bigseeproduction.game.level.tiles;

import com.bigseeproduction.game.gfx.Screen;
import com.bigseeproduction.game.level.Level;

/**
 * Created by Luca on 07.04.2015.
 */
public class BasicTile extends Tile {


    protected int tileID;
    protected int tileColor;

    public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, false, false, levelColor);
        this.tileID = x + y * 32;
        this.tileColor = tileColor;
    }

    public void tick() {

    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {
        screen.render(x, y, tileID, tileColor, 0x00, 1);
    }
}
