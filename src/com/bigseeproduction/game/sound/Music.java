package com.bigseeproduction.game.sound;

import com.bigseeproduction.game.Game;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by Luca on 11.04.2015.
 */
public enum Music {

    LAUNCHER("/music/Arcade80kbps.mp3", SoundType.MUSIC),
    MAIN("/music/A Journey Awaits.mp3", SoundType.MUSIC);


    private AudioInputStream audioInputStream;
    private URL url;

    private Player player;

    private int size;
    private byte[] audio;

    private SoundType type;
    private boolean overrideMute;

    private Thread thread;

    Music(String soundFileName, SoundType type, boolean overrideMute) {

        try {
            this.type = type;
            this.overrideMute = overrideMute;

            url = Game.class.getResource(soundFileName);
        } catch (Exception e) {
            Game.LOGGER.log(Level.SEVERE, e.toString());
            e.printStackTrace();
        }
    }

    Music(String soundFileName, SoundType type) {
        this(soundFileName, type, false);
    }

    public boolean play() {
        Game.LOGGER.log(Level.FINER, "Play music");
        if (!((Game.playSounds && this.type == SoundType.SOUND) || (Game.playMusic && this.type == SoundType.MUSIC)) && !this.overrideMute)
            return false;


        thread = new Thread(() -> {
            try {

                this.audioInputStream = AudioSystem.getAudioInputStream(url);

                AudioFormat af = audioInputStream.getFormat();

                size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
                audio = new byte[size];
                byte[] audioTemp = audio.clone();

                audioInputStream.read(audioTemp, 0, size);
                player = new Player(audioInputStream);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Game.NAME + "_music");
        thread.start();

        return true;
    }

    public void dispose() {
        player.close();
        try {
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loop() {
        Game.LOGGER.log(Level.FINER, "Play Sound");
        if (!((Game.playSounds && this.type == SoundType.SOUND) || (Game.playMusic && this.type == SoundType.MUSIC)) && !this.overrideMute)
            return false;


        thread = new Thread(() -> {
            try {
                while (true) {
                    this.audioInputStream = AudioSystem.getAudioInputStream(url);

                    AudioFormat af = audioInputStream.getFormat();

                    size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
                    audio = new byte[size];
                    byte[] audioTemp = audio.clone();

                    audioInputStream.read(audioTemp, 0, size);
                    player = new Player(audioInputStream);
                    player.play();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Game.NAME + "_music");
        return true;
    }


    public void stop() {
        try {
            if (player != null)
                player.close();
            audioInputStream.close();
            if (thread != null)
                thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOverrideMute() {
        return overrideMute;
    }

    public void setOverrideMute(boolean overrideMute) {
        this.overrideMute = overrideMute;
    }
}
