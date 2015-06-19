package com.bigseeproduction.game.level;

/**
 * Created by Luca on 12.04.2015.
 */
public enum Levels {
    SMALL_TEST_LEVEL("Small test level", "/levels/small_test_level.png", 100, 100),
    MAIN_MENU_LEVEL("Main menu level", "/levels/main_menu_level.png", 100, 100),
    MENU_LEVEL("menu level", "/levels/menu_level.png", 400, 400);


    private String filePath;
    private String name;
    private int startX;
    private int startY;

    Levels(String name, String filePath, int startX, int startY) {
        this.name = name;
        this.filePath = filePath;
        this.startX = startX;
        this.startY = startY;
    }


    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartX() {
        return startX;
    }
}
