package com.bigseeproduction.game.level.tiles;

/**
 * Created by Luca on 10.04.2015.
 */
public class BasicSolidTile extends BasicTile {

    public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, x, y, tileColor, levelColor);
        this.solid = true;
    }
}
