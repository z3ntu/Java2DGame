package com.bigseeproduction.game.entities;

import com.bigseeproduction.game.gfx.Screen;
import com.bigseeproduction.game.level.Level;

/**
 * Created by Luca on 07.04.2015.
 */
public abstract class Entity {

    public int x, y;
    protected Level level;

    public Entity(Level level) {
        init(level);
    }

    public final void init(Level level) {
        this.level = level;
    }

    public abstract void tick();

    public abstract void render(Screen screen);

    public void setLevel(Level level) {
        this.level = level;
    }

}
