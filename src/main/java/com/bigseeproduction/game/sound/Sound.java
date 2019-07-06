package com.bigseeproduction.game.sound;

import com.bigseeproduction.game.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created by Luca on 11.04.2015.
 */
public enum Sound {

    SELECT("/sounds/select.wav", SoundType.SOUND),
    HURT("/sounds/hurt.wav", SoundType.SOUND);

    private Clip clip;

    private SoundType type;
    private boolean overrideMute;

    Sound(String soundFileName, SoundType type, boolean overrideMute) {
        try {
            this.type = type;
            this.overrideMute = overrideMute;

            URL url = Game.class.getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
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

        // Sometimes a sound should play again even though it hasn't finished playing yet
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();

        return true;
    }

    public void dispose() {
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void setVolume(int volume) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }
}
