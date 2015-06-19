package com.bigseeproduction.game.entities;

import com.bigseeproduction.game.level.Level;
import com.bigseeproduction.game.level.tiles.Tile;

/**
 * Created by Luca on 07.04.2015.
 */
public abstract class Mob extends Entity {

    protected String name;
    protected int movingSpeed;
    protected int numSteps = 0;
    protected boolean moving;
    protected int movingDir = 1;
    protected int scale;

    public Mob(Level level, String name, int x, int y, int movingSpeed, int scale) {
        super(level);
        this.name = name;
        this.movingSpeed = movingSpeed;
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public boolean move(int xa, int ya) {
        if (xa != 0 && ya != 0) {
            numSteps--;
            return move(xa, 0) | move(0, ya);
        }
        numSteps++;
        if (!hasCollided(xa, ya)) {
            if (ya < 0) movingDir = 0;
            if (ya > 0) movingDir = 1;
            if (xa < 0) movingDir = 2;
            if (xa > 0) movingDir = 3;
            x += xa * movingSpeed;
            y += ya * movingSpeed;
            return true;
        }
        return false;
    }

    public abstract boolean hasCollided(int xa, int ya);

    protected boolean isSolidTile(int xa, int ya, int x, int y) {
        if (level == null) return false;
        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
        return !lastTile.equals(newTile) && newTile.isSolid();
    }

    public String getName() {
        return name;
    }
}
