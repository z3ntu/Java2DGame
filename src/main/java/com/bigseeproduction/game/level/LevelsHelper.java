package com.bigseeproduction.game.level;

import com.bigseeproduction.game.Game;

import java.util.logging.Level;

/**
 * Created by Luca on 12.04.2015.
 */
public class LevelsHelper {

    public static String[] getLevelsNamesAsArray() {
        String[] ret = new String[1 + Levels.values().length];
        ret[0] = "Back to Menu";
        int index = 1;
        for (Levels l : Levels.values()) {
            ret[index++] = l.getName();
        }

        return ret;
    }

    /**
     * Loads level according to the index of the Enum
     *
     * @param selected the selected entry; 0=back to menu; so index+1
     */
    public static void handleLoadLevels(Game game, int selected) {
        try {
            Levels[] val = Levels.values();
            game.changeLevel(val[selected - 1]);
        } catch (Exception e) {
            Game.LOGGER.log(Level.SEVERE, e.toString());
        }
    }

}
