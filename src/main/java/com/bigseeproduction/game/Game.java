package com.bigseeproduction.game;

import com.bigseeproduction.game.entities.Player;
import com.bigseeproduction.game.gfx.Colors;
import com.bigseeproduction.game.gfx.Screen;
import com.bigseeproduction.game.gfx.SpriteSheet;
import com.bigseeproduction.game.level.Level;
import com.bigseeproduction.game.level.Levels;
import com.bigseeproduction.game.listener.InputHandler;
import com.bigseeproduction.game.listener.MouseHandler;
import com.bigseeproduction.game.menus.MainMenu;
import com.bigseeproduction.game.menus.OptionMenu;
import com.bigseeproduction.game.menus.PauseMenu;
import com.bigseeproduction.game.sound.Music;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Luca on 07.04.2015.
 */
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 6;
    public static final Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
    public static final String NAME = "Game";
    public static final String VERSION = "v0.1";
    public static final Logger LOGGER = Logger.getLogger(Game.class.getName());
    public static boolean debugScreen = false;
    public static boolean playMusic = true;
    public static boolean playSounds = true;
    public static GameState gameState = GameState.NONE;

    //    private JFrame frame;
    public int tickCount = 0;
    public InputHandler input;
    public MouseHandler mouse;
    public Level level;
    public Player player;
    public BufferedImage dimmImage;
    private Thread thread;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colors = new int[6 * 6 * 6];
    private Screen screen;
    private int fps;
    private int tps;
    private MainMenu mainMenu;
    private PauseMenu pauseMenu;
    private OptionMenu optionMenu;
    private GameLauncher launcher;
    private boolean shouldChangeLevel = false;
    private Levels changeToLevel;


    public Game(GameLauncher launcherPar) {
        this.launcher = launcherPar;

        System.out.println(dimension);

        launcher.frame.setMinimumSize(dimension);
        launcher.frame.setMaximumSize(dimension);
        launcher.frame.setPreferredSize(dimension);

/*        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);*/


//        launcher.frame.setVisible(false);

//        frame = new JFrame(NAME);

        launcher.frame.setTitle(NAME);

//      launcher.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcher.frame.setLayout(new BorderLayout());

        launcher.frame.add(this, BorderLayout.CENTER);
        launcher.frame.pack();

        launcher.frame.setResizable(true);
        launcher.frame.setLocationRelativeTo(null);
//        launcher.frame.setVisible(true);
    }

    public void init() {
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colors[index++] = rr << 16 | gg << 8 | bb;

                }
            }
        }

        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/spritesheet.png"));
        input = new InputHandler(this);
        mouse = new MouseHandler(this);
        level = new Level(Levels.MENU_LEVEL);
//        level = new Level(null);
        player = new Player(this, level, 200, 200, input, 0, 28, Colors.getColor(-1, 111, 145, 543));
        level.addEntity(player);
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this);
        optionMenu = new OptionMenu(this);
        try {
            dimmImage = ImageIO.read(Game.class.getResource("/images/dimm.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Music.MAIN.play();


    }

    public synchronized void start() {
        gameState = GameState.MAINMENU;
        thread = new Thread(this, Game.NAME + "_main");
        thread.start();
    }

    public synchronized void stop() {
        gameState = GameState.STOPPED;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Game.LOGGER.log(java.util.logging.Level.SEVERE, e.toString());
        }
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (gameState != GameState.STOPPED) {
            if (shouldChangeLevel) {
                this.changeLevelExec();
            }
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                fps = frames;
                tps = ticks;
                frames = 0;
                ticks = 0;


            }

        }
    }


    public void tick() {

        tickCount++;

        if (level != null) {
            level.tick();
        }

//        System.out.println(Sound.MAIN.getFramePosition());


    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);

        if (level != null) {
            level.renderTiles(screen, xOffset, yOffset);
            //Font.render("ABCDEFGHIJK", screen, 0, 0, Colors.getColor(-1, -1, -1, 555));
        }
        if (level != null) {
            level.renderEntities(screen);
        }
        //Font.showMiddleText("Important message!!", screen, Colors.getColor(-1, -1, -1, 555), 1);


        /**
         * Converting...
         */
        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colorCode = screen.pixels[x + y * screen.width];
                if (colorCode < 255) pixels[x + y * screen.width] = colors[colorCode];
            }
        }

        Graphics g = bs.getDrawGraphics();

        /**
         * Draw the actual image...
         */
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        if (gameState == GameState.MAINMENU) {
            mainMenu.drawMenu(g);
        }
        if (gameState == GameState.SELECTLEVEL) {
            mainMenu.drawLevels(g);
        }
        if (gameState == GameState.PAUSEMENU) {
            pauseMenu.drawMenu(g);
        }
        if (gameState == GameState.OPTIONMENU) {
            optionMenu.drawMenu(g);
        }

        if (debugScreen) {

            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawString(NAME + " " + VERSION, 5, 20);
            g.drawString("FPS: " + fps + " - Ticks: " + tps, 5, 50);
            g.drawString("X: " + player.x + " - Y: " + player.y, 5, 80);
            g.drawString("Level: " + level.getImagePath(), 5, 110);
            g.drawString("GameState: " + this.gameState, 5, 140);

        }

        g.dispose();
        bs.show();
    }
/*
    public static void debug(String msg, LogLevel level){
        String prefix = "[" + NAME + "] ";
        if(level != LogLevel.INFO && !debugMessages) return;
        switch(level){
            case DEBUG_INFO:
                System.out.println(prefix+msg);
                writer.println(prefix+msg);
                break;
            case INFO:
                System.out.println(prefix + "[INFO]" + msg);
                break;
            case WARNING:
                System.out.println(prefix + "[WARNING] " + msg);
                break;
            case FATAL:
                System.err.println(prefix + "[FATAL!!] " + msg);
                break;
        }
    }
*/
/*
    public void changeLevel(Levels level){
        EventQueue.invokeLater(() -> {
            this.level.removeEntity(player);
            this.level = null;
            this.level = new Level(level);
            player.setLevel(this.level);
            player.x = 100;
            player.y = 100;
            this.level.addEntity(player);
        });
        //TODO: Change Level as soon as the tick is over!!!


    }
*/

    public void changeLevel(Levels level) {
        changeToLevel = level;
        shouldChangeLevel = true;
    }

    private void changeLevelExec() {
        this.level.removeEntity(player);
        this.level = null;
        this.level = new Level(changeToLevel);
        player.setLevel(this.level);
        player.x = 100;
        player.y = 100;
        this.level.addEntity(player);
        shouldChangeLevel = false;
    }


}
