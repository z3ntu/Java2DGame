package com.bigseeproduction.game.sound;

import com.bigseeproduction.game.Game;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by Luca on 11.04.2015.
 */
public enum Sound {

    SELECT("/sounds/select.wav", SoundType.SOUND),
    HURT("/sounds/hurt.wav", SoundType.SOUND);
//    LAUNCHER("/music/test.mp3", SoundType.MUSIC),
//    MAIN("/music/A_Journey_Awaits.wav", SoundType.MUSIC),
//
//    TEST("/music/test.mp3", SoundType.MUSIC);


    private URL url;
    private Clip clip;

    private AudioInputStream audioInputStream;
    private BufferedInputStream bis;
    private AudioFormat af;
    private int size;
    private byte[] audio;
    private DataLine.Info info;

    private Player player;


    private SoundType type;
    private boolean overrideMute;

    Sound(String soundFileName, SoundType type, boolean overrideMute) {

        try {
            this.type = type;
            this.url = Game.class.getResource(soundFileName);
            this.overrideMute = overrideMute;

            this.audioInputStream = AudioSystem.getAudioInputStream(url);
//            FileInputStream fis = new FileInputStream(this.url.getFile());
            FileInputStream fis = (FileInputStream) Game.class.getResourceAsStream(url.getFile());

            bis = new BufferedInputStream(fis);
            this.af = audioInputStream.getFormat();
            size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            this.audio = new byte[size];
            info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            player = new Player(audioInputStream);

/*
                clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    this.clip.stop();
                }
            });
*/
        } catch (Exception e) {
            Game.LOGGER.log(Level.SEVERE, e.toString());
            e.printStackTrace();
        }
    }

    Sound(String soundFileName, SoundType type) {
        this(soundFileName, type, false);
    }

    public boolean play() {
        Game.LOGGER.log(Level.FINER, "Play Sound");
        if (!((Game.playSounds && this.type == SoundType.SOUND) || (Game.playMusic && this.type == SoundType.MUSIC)) && !this.overrideMute)
            return false;
//        System.out.println("really play sound!");
/*
        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
*/
        try {
            clip = (Clip) AudioSystem.getLine(info);
//            if(!clip.isOpen())
            clip.open(af, audio, 0, size);
            clip.start();
            return true;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void dispose() {
        //clip.close();
    }

    public void loop() {
        try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
/*
    public void loop(int times) {
        clip.loop(times);
    }
*/

    public void stop() {
        clip.stop();
//        player.close();
    }

    public int getFramePosition() {
        return clip.getFramePosition();
    }

    public void setFramePosition(int framePosition) {
        clip.setFramePosition(framePosition);
    }

    public void toBeginning() {
        //clip.setFramePosition(0);
    }

    public void setVolume(int volume) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }

    public int getFrameLength() {
        return clip.getFrameLength();
    }

    public boolean isOverrideMute() {
        return overrideMute;
    }

    public void setOverrideMute(boolean overrideMute) {
        this.overrideMute = overrideMute;
    }
}
