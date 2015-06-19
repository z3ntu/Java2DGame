package com.bigseeproduction.game;

import com.bigseeproduction.game.listener.WindowHandler;
import com.bigseeproduction.game.sound.Music;
import com.bigseeproduction.game.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Luca on 11.04.2015.
 */
public class GameLauncher {

    JFrame frame;
    private JButton startButton;
    private JButton closeButton;
    private JCheckBox sound;
    private JCheckBox music;


    public GameLauncher() {
        init();
    }

    public static void main(String[] args) {
        new GameLauncher();
    }

    public void init() {
        frame = new JFrame("Game Launcher");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.addWindowListener(new WindowHandler());

        startButton = new JButton("Start Game!");
        startButton.addActionListener(e -> startGame());
        closeButton = new JButton("Close Launcher");
        closeButton.addActionListener(e -> System.exit(0));

        sound = new JCheckBox("Sounds?");
        music = new JCheckBox("Music?");
        sound.addActionListener(new CheckboxListener());
        music.addActionListener(new CheckboxListener());
        sound.setSelected(true);
        music.setSelected(true);

        frame.add(startButton);
        frame.add(closeButton);
        frame.add(sound);
        frame.add(music);

        Music.LAUNCHER.play();


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public void startGame() {
        Music.LAUNCHER.stop();
        Sound.SELECT.play();
        Game.playSounds = sound.isSelected();
        Game.playMusic = music.isSelected();

        Music.LAUNCHER.setOverrideMute(false);
        Music.LAUNCHER.dispose();

        frame.remove(startButton);
        frame.remove(closeButton);
        frame.remove(sound);
        frame.remove(music);

        //NEVER frame.removeAll()!!!!!
        new Game(this).start();
    }

    private class CheckboxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == music) {
                Music.LAUNCHER.setOverrideMute(music.isSelected());
                if (music.isSelected()) {
//                    Sound.LAUNCHER.toBeginning();
                    Music.LAUNCHER.loop();
                } else {
                    Music.LAUNCHER.stop();
                }
            } else if (e.getSource() == sound) {

            }

        }
    }

}
